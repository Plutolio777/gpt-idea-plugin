package cloud.jlkjy.cn.gpt.ui;

import cloud.jlkjy.cn.gpt.type.Fonts;
import cloud.jlkjy.cn.gpt.type.Icons;
import cloud.jlkjy.cn.gpt.ui.components.ChatPanel;
import cloud.jlkjy.cn.gpt.ui.components.DisplayIcon;
import cloud.jlkjy.cn.gpt.ui.components.FixedHeightLabel;
import cloud.jlkjy.cn.gpt.ui.components.TabLabel;
import cloud.jlkjy.cn.gpt.ui.components.popup.PopupPanel;
import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.util.ui.JBUI;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
@Setter
public class GptCodeGenWindowPanel extends JPanel {

    private final Project project;
    private final ToolWindow toolWindow;
    private JPanel headerPanel;
    private JPanel headerContentPanel;
    private JPanel contentPanel;
    private JLabel loginBtn;
    private JPanel tabPanel;
    private JPanel toolbarPanel;

    private JLabel chatBtn;
    private JLabel searchBtn;
    private JPanel notLoginBarPanel;
    private JPanel LoginBarPanel;
    private JLabel createSessionBtn;
    private Box logginBox;
    private JLabel historySessionBtn;
    private JPanel userInfoPanel;
    private JLabel userAvatar;
    private JLabel userName;
    private JLabel popupIconLabel;
    private JPanel chatPanel;

    private PopupPanel popupPanel;

    public GptCodeGenWindowPanel(Project project, ToolWindow toolWindow) {
        this.project = project;
        this.toolWindow = toolWindow;
        setLayout(new BorderLayout());
        this.setFont(Fonts.Default);
        this.setUpBasePanel();
        this.setUpComponents();
    }


    private void setUpBasePanel() {

        this.headerPanel = new JPanel(new BorderLayout());
        this.headerContentPanel = new JPanel(new BorderLayout());
        this.contentPanel = new JPanel(new BorderLayout());

        this.add(this.headerPanel, BorderLayout.NORTH);
        this.add(this.contentPanel, BorderLayout.CENTER);

        this.headerPanel.setBorder(JBUI.Borders.customLine(ColorUtil.getEditorBackgroundColor(), 0, 0, 1, 0));
        this.headerPanel.setPreferredSize(new Dimension(204, 41));

        this.headerContentPanel.setBorder(JBUI.Borders.empty(0, 12));
        this.headerContentPanel.setPreferredSize(new Dimension(204, 40));
        this.headerPanel.add(this.headerContentPanel, BorderLayout.CENTER);


        // 左侧 Tab 面板
        this.tabPanel = new JPanel(new BorderLayout());

        // 右侧 Toolbar 面板
        this.toolbarPanel = new JPanel(new BorderLayout());


        this.headerContentPanel.add(tabPanel, BorderLayout.WEST);
        this.headerContentPanel.add(toolbarPanel, BorderLayout.EAST);
    }



    private void setUpComponents() {
        this.setUpTabBar();
        this.setUpToolBar();
        this.setUpChatPanel();
    }

    private void setUpChatPanel() {
        this.chatPanel = new ChatPanel(this.project, this.toolWindow, this);
        this.contentPanel.add(this.chatPanel, BorderLayout.CENTER);
    }


    private void setUpTabBar() {
        Box container = Box.createHorizontalBox();
        this.chatBtn = new TabLabel("智能问答", 40);

        setupTabColor(chatBtn);
        this.searchBtn = new TabLabel("搜索", 40);

        setupTabColor(searchBtn);
        container.add(chatBtn);

        container.add(searchBtn);
        this.tabPanel.add(container, BorderLayout.CENTER);
    }

    private void setUpToolBar() {
        this.buildNotLoginBar();
        this.buildLoginBar();

        toolbarPanel.add(this.LoginBarPanel, BorderLayout.CENTER);
    }

    private void buildNotLoginBar() {
        this.notLoginBarPanel = new JPanel(new BorderLayout());
        this.loginBtn = new JLabel("登录");
        this.loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.loginBtn.setFont(this.loginBtn.getFont().deriveFont(14.0F));
        this.notLoginBarPanel.add(this.loginBtn, BorderLayout.CENTER);
    }

    private void  buildLoginBar() {
        this.buildUserInfoPanel();
        this.LoginBarPanel = new JPanel(new BorderLayout());
        this.logginBox = Box.createHorizontalBox();
        this.createSessionBtn = new DisplayIcon(Icons.Add);
        this.historySessionBtn = new DisplayIcon(Icons.History);
        JLabel sepLabel = new JLabel(" ");
        sepLabel.setBorder(JBUI.Borders.customLine(ColorUtil.getEditorBackgroundColor(), 0, 0, 0, 2));
        this.logginBox.add(createSessionBtn);
        this.logginBox.add(Box.createHorizontalStrut(12));
        this.logginBox.add(historySessionBtn);
        this.logginBox.add(Box.createHorizontalStrut(12));
        this.logginBox.add(sepLabel);
        this.logginBox.add(Box.createHorizontalStrut(12));
        this.logginBox.add(this.userInfoPanel);

        this.LoginBarPanel.add(this.logginBox, BorderLayout.CENTER);
    }

    private void buildUserInfoPanel() {
        this.userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        this.userAvatar = new JLabel(Icons.DefaultAvatar);
        this.userName = new JLabel("liuyijun");
        this.popupIconLabel = (JLabel)new FixedHeightLabel(Icons.ArrowDown, 40);
        this.userInfoPanel.add(userAvatar);
        this.userInfoPanel.add(userName);
        this.userInfoPanel.add(this.popupIconLabel);
        this.userInfoPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void setupTabColor(final JLabel label) {
        label.setBackground(ColorUtil.getTabbedBgColor());
        label.setBorder((Border)JBUI.Borders.empty(0, 12));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                label.setOpaque(true);
                label.setBackground(ColorUtil.getTabbedHoverBgColor());
                label.invalidate();
                label.repaint();
            }

            public void mouseExited(MouseEvent e) {
                label.setOpaque(false);
                label.setBackground(ColorUtil.getTabbedBgColor());
                label.invalidate();
                label.repaint();
            }
        });
    }

}
