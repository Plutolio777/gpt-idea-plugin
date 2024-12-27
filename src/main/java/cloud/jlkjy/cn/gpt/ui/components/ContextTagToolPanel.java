package cloud.jlkjy.cn.gpt.ui.components;

import cloud.jlkjy.cn.gpt.layout.ChatContextTagLayout;
import cloud.jlkjy.cn.gpt.model.ChatContextRecord;
import cloud.jlkjy.cn.gpt.type.Icons;
import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.JBUI;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ContextTagToolPanel extends JPanel {

    private Project project;

    public ContextTagToolPanel(Project project) {
        this.project = project;
        setBackground(ColorUtil.getEditorBackgroundColor());
        setBorder((Border) JBUI.Borders.emptyBottom(8));
        setLayout((LayoutManager) new ChatContextTagLayout(4, 4, 24));
        addTag(ChatContextRecord.builder().contextId("xxxxx").text("ContextTagToolPanel.java").icon(AllIcons.Nodes.JavaModule).startLine(13).endLine(24).build());
    }

    public void setBackground(Color bg) {
        super.setBackground(ColorUtil.getEditorBackgroundColor());
    }

    public void addTag(ChatContextRecord tag) {
        if (!isEnabled())
            return;
        if (StringUtils.isBlank(tag.getText()))
            return;
//        removeEmptyTooltip();
        Boolean needSetup = Boolean.TRUE;
//        if (this.chatInputContext.getInputMainPanel() == null)
//            needSetup = Boolean.FALSE;
        ChatContextTagButton askTag = buildTagButton(tag, needSetup);
//        if (ChatContextTypeEnum.IMAGE.getType().equals(tag.getType())) {
//            if (StringUtils.isNotBlank(tag.getRemoteFileUrl())) {
//                askTag.setIcon(LingmaIcons.ImageGreyIcon);
//                askTag.setValid(true);
//            } else {
//                askTag.setIcon(LingmaIcons.ImageFailRedIcon);
//                askTag.setValid(false);
//            }
//        } else if (!tag.getActualValid().booleanValue()) {
//            askTag.setValid(false);
//        }
        add((Component) askTag);
        revalidate();
        repaint();
    }



    private ChatContextTagButton buildTagButton(ChatContextRecord tag, Boolean needSetup) {
//        boolean enableDrag = (this.chatInputContext.getInputMainPanel() != null);
        return new ChatContextTagButton(this.project, this, tag, true);
    }
}
