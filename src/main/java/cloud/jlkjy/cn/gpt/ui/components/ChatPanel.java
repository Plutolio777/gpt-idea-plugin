package cloud.jlkjy.cn.gpt.ui.components;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {

    private JBScrollPane messagePanel;
    private JPanel askPanel;

    public ChatPanel() {
        setLayout(new BorderLayout());
        this.setBorder(JBUI.Borders.empty());
        setUpBasePanel();
    }

    private void setUpBasePanel() {
        this.messagePanel = new JBScrollPane();
        this.askPanel = new AskInputPanel();
        this.add(messagePanel, BorderLayout.CENTER);
        this.add(askPanel, BorderLayout.SOUTH);

    }


}
