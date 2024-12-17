package cloud.jlkjy.cn.gpt.ui.components;

import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HyperlinkButton extends JLabel {
    private final Color normalColor = JBColor.BLUE; // 默认颜色
    private boolean underline = false;           // 控制下划线状态

    public HyperlinkButton(String text) {
        super(text);
        setOpaque(true);// 添加下划线
        setBackground(ColorUtil.getEditorBackgroundColor());
        setForeground(normalColor); // 设置字体颜色
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 鼠标悬停手形光标

        // 添加鼠标事件监听器
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                underline = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                underline = false;
                repaint();
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (underline) {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(getText().replaceAll("<.*?>", "")); // 过滤HTML标签
            int x = getInsets().left;
            int y = getHeight() - getInsets().bottom - 1;
            g.drawLine(x, y, x + textWidth, y);
        }
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(ColorUtil.getEditorBackgroundColor());
    }
}
