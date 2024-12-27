package cloud.jlkjy.cn.gpt.ui.components;

import cloud.jlkjy.cn.gpt.type.Fonts;
import cloud.jlkjy.cn.gpt.type.Icons;
import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ChatReturnButton extends RichRoundRectPanel{

    private final JLabel iconLabel;
    private final JLabel label;

    public ChatReturnButton() {
        super(new BorderLayout());
        setCornerRadius(8);
        this.iconLabel = new JLabel(Icons.Return);
        this.iconLabel.setPreferredSize(new Dimension(20, 20));
        this.iconLabel.setFont(Fonts.LABEL_DEFAILT_FONT);
        this.iconLabel.setHorizontalAlignment(0);
        this.iconLabel.setForeground(ColorUtil.getLabelForegroundColor());
        add(this.iconLabel, BorderLayout.WEST);

        this.label = new JLabel(" Enter ");
        this.label.setFont(Fonts.LABEL_DEFAILT_FONT);
        this.label.setHorizontalAlignment(0);
        this.label.setForeground(ColorUtil.getLabelForegroundColor());
        add(this.label, BorderLayout.CENTER);

        setBorder(JBUI.Borders.empty(4));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder((Border) JBUI.Borders.empty());
        setDefaultBackgoundColor(ColorUtil.getToolWindowBackgroundColor());
        setHoverBackgoundColor(ColorUtil.getItemSelectionBackground());
        setSelectedBackgoundColor(ColorUtil.getLinkForegroundColor());
        foreachChildComponent((JComponent) this, component -> component.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (component.isEnabled())
                    ChatReturnButton.this.handleClick();
            }
        }));
        setup();
//        setupKeyListener(tagsPanel.getChatInputContext().getProject());
    }

    public void handleClick() {
        Messages.showInputDialog(
                "请输入您的名字：",   // 消息内容
                "输入名字",         // 标题
                Messages.getQuestionIcon() // 图标
        );
    }

}
