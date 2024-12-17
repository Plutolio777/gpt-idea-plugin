package cloud.jlkjy.cn.gpt.ui;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;

import java.awt.BorderLayout;
import javax.swing.*;

public class Test extends JPanel {
    private JPanel mainPanel;

    public Test() {
        this.mainPanel.setBorder(JBUI.Borders.customLine(JBColor.WHITE, 0, 0, 1, 0));
    }
}
