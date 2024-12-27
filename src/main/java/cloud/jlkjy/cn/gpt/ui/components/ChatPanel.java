package cloud.jlkjy.cn.gpt.ui.components;

import cloud.jlkjy.cn.gpt.ui.GptCodeGenWindowPanel;
import cloud.jlkjy.cn.gpt.ui.components.popup.PopupPanel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class ChatPanel extends JPanel {

    private final Project project;
    private final ToolWindow toolWindow;
    private final GptCodeGenWindowPanel mainPanel;
    private JBScrollPane messagePanel;
    private JPanel askPanel;
    private PopupPanel popupPanel;

    public ChatPanel(Project project, ToolWindow toolWindow, GptCodeGenWindowPanel gptCodeGenWindowPanel) {
        this.project = project;
        this.toolWindow = toolWindow;
        this.mainPanel = gptCodeGenWindowPanel;
        setLayout(new BorderLayout());
        this.setBorder(JBUI.Borders.empty());
        setUpBasePanel();
    }

    private void setUpBasePanel() {
        this.popupPanel = new PopupPanel(this.project);
        this.messagePanel = new JBScrollPane();
        this.askPanel = new AskInputPanel(this.project, this.toolWindow, this);
        this.add(messagePanel, BorderLayout.CENTER);
        this.add(askPanel, BorderLayout.SOUTH);
    }


}
