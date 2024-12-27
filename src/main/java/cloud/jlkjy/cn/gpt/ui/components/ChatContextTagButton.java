package cloud.jlkjy.cn.gpt.ui.components;

import cloud.jlkjy.cn.gpt.model.ChatContextRecord;
import cloud.jlkjy.cn.gpt.type.Fonts;
import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import cloud.jlkjy.cn.gpt.utils.SwingUtil;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.JBUI;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
@Setter
public class ChatContextTagButton extends BaseChatTagButton{

    private JLabel closeLabel;
    private JLabel lineRangeLabel;
    private Project project;
    private ContextTagToolPanel tagsPanel;
    private JLabel label;
    private ChatContextRecord tag;


    public ChatContextTagButton(Project project, ContextTagToolPanel tagPanel, ChatContextRecord tag, boolean enableDrag) {
        super(new BorderLayout());
        this.project = project;
        this.tagsPanel = tagPanel;
        this.tag = tag;
        setCornerRadius(8);

        this.label = new JLabel(tag.getText());
        this.label.setHorizontalAlignment(2);
        this.label.setFont(Fonts.LABEL_TAG_SMALL_FONT);
        this.label.setIconTextGap(8);
        if (tag.getIcon() != null) {
            this.label.setIcon(tag.getIcon());
        }
//        this.label.setUI(new CustomLabelUI());
        add(this.label, "Center");

        Box rightBox = Box.createHorizontalBox();
        if (tag.getStartLine() != null && tag.getEndLine() != null) {
            rightBox.add(Box.createHorizontalStrut(2));
            this.lineRangeLabel = new JLabel(tag.getStartLine() + "-" + tag.getEndLine());
            this.lineRangeLabel.setHorizontalAlignment(0);
            this.lineRangeLabel.setFont(Fonts.LABEL_TAG_SMALL_FONT);
            this.lineRangeLabel.setForeground(ColorUtil.getAuxiliaryForegroundColor());
            rightBox.add(this.lineRangeLabel);
        }
        rightBox.add(Box.createHorizontalStrut(2));
        this.closeLabel = new JLabel(AllIcons.Actions.Close);
        this.closeLabel.setFont(Fonts.LABEL_TAG_SMALL_FONT);
        this.closeLabel.setHorizontalAlignment(0);
        rightBox.add(this.closeLabel);
        add(rightBox, "East");
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder((Border) JBUI.Borders.empty(2, 4));
        setDefaultBackgoundColor(ColorUtil.getToolWindowBackgroundColor());
        setHoverBackgoundColor(ColorUtil.getItemSelectionBackground());
        setSelectedBackgoundColor(ColorUtil.getLinkForegroundColor());
        setup();
    }

    @Override
    public void handleClick() {

    }

    public void setIcon(Icon icon) {
        if (icon != null) {
            this.label.setIcon(icon);
            revalidate();
            repaint();
        }
    }

    public void setText(String text) {
        if (text != null) {
            this.label.setText(text);
            revalidate();
            repaint();
        }
    }



}
