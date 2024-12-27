package cloud.jlkjy.cn.gpt.ui.components;

import cloud.jlkjy.cn.gpt.ui.GptCodeGenWindowPanel;
import cloud.jlkjy.cn.gpt.ui.components.popup.PopupPanel;
import cloud.jlkjy.cn.gpt.ui.components.textpanel.ChatTextPanel;
import cloud.jlkjy.cn.gpt.ui.components.textpanel.RichTextPanel;
import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import cloud.jlkjy.cn.gpt.utils.SwingUtil;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

@Getter
@Setter
public class AskInputPanel extends JPanel {
    private final Project project;
    private final ToolWindow toolWindow;
    private final ChatPanel chatPanel;
    private final PopupPanel popupPanel;
    JPanel basePanel;
    RoundRectPanel inputPanel;
    RichTextPanel textArea;
    JBScrollPane scrollPane;
    JPanel toolbarPanel;
    private JPanel inputBasePanel;
    private RichTextPanel textPanel;
    private JPanel footBarPanel;
    private HyperlinkButton hyperlinkButton;
    private ChatReturnButton returnBtn;

    public AskInputPanel(Project project, ToolWindow toolWindow, ChatPanel chatPanel) {
        this.project = project;
        this.toolWindow = toolWindow;
        this.chatPanel = chatPanel;
        setLayout(new BorderLayout());
        this.setUpBasePanel();
        this.setBorder(JBUI.Borders.customLine(ColorUtil.getEditorBackgroundColor(), 1, 0, 0, 0));
        this.popupPanel = new PopupPanel(this.project);
    }

    private void setUpBasePanel() {
        this.basePanel = new JPanel(new BorderLayout());
        this.basePanel.setBorder(JBUI.Borders.empty(10, 12));

        this.inputPanel = new RoundRectPanel(new BorderLayout()) {
            public void setBackground(Color bg) {
                super.setBackground(ColorUtil.getEditorBackgroundColor());
            }

            public void refreshColor(EditorColorsScheme scheme, Color themeColor) {
                setBackground(ColorUtil.getEditorBackgroundColor());
            }
        };

        this.inputPanel.setBorder((Border)JBUI.Borders.empty(8, 12, 8, 10));
        this.inputPanel.setBackground(ColorUtil.getEditorBackgroundColor());
        this.basePanel.add(this.inputPanel, BorderLayout.CENTER);


        this.add(this.basePanel, BorderLayout.CENTER);

        this.toolbarPanel = new ContextTagToolPanel(project);

        this.inputPanel.add(this.toolbarPanel, BorderLayout.NORTH);

        this.footBarPanel = new JPanel(new BorderLayout()){
            public void setBackground(Color bg) {
                super.setBackground(ColorUtil.getEditorBackgroundColor());
            }

            public void refreshColor(EditorColorsScheme scheme, Color themeColor) {
                setBackground(ColorUtil.getEditorBackgroundColor());
            }
        };
        this.footBarPanel.setBorder(JBUI.Borders.empty(3));


        this.buildFootBarTools();
        this.inputBasePanel = new JPanel(new BorderLayout());
        this.textPanel = new ChatTextPanel();
        this.textPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                AskInputPanel.this.updateLayout();
            }
        });



        this.scrollPane = new JBScrollPane(10) {
            public Dimension getPreferredSize() {
                Dimension textDim = AskInputPanel.this.textPanel.getPreferredSize();
                return new Dimension(textDim.width, Math.min(textDim.height, 300));
            }

//            public void doLayout() {
//                super.doLayout();
//                AskInputPanel.this.updateSendLayout();
//            }
        };
        this.scrollPane.setViewportView(this.textPanel);
        this.scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.scrollPane.setHorizontalScrollBarPolicy(31);
        this.scrollPane.setVerticalScrollBarPolicy(20);
        this.inputPanel.add(this.scrollPane, BorderLayout.CENTER);
        this.inputPanel.add(this.footBarPanel, BorderLayout.SOUTH);
        this.inputPanel.repaint();
    }

    private void buildFootBarTools() {
        this.hyperlinkButton = new HyperlinkButton("@ 添加引用");
        this.hyperlinkButton.setBorder(JBUI.Borders.empty(0, 1));
        this.hyperlinkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AskInputPanel.this.handleClick();
            }
        });


        this.returnBtn = new ChatReturnButton();

        this.footBarPanel.add(hyperlinkButton, BorderLayout.WEST);
        this.footBarPanel.add(this.returnBtn, BorderLayout.EAST);
    }

    public void updateLayout() {
        revalidate();
        repaint();
    }


    public void handleClick() {
//        PopupPanel popupPanel = AskInputPanel.this.getChatPanel().getMainPanel().getPopupPanel();
        popupPanel.displaySuggestPrompts("function", this.inputPanel.getLocationOnScreen(), "",  this.inputPanel);
    }



}
