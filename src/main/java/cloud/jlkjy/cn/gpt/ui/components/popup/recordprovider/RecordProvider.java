package cloud.jlkjy.cn.gpt.ui.components.popup.recordprovider;

import javax.swing.*;
import java.util.List;

import cloud.jlkjy.cn.gpt.model.PopupRecord;
import com.intellij.openapi.project.Project;

public interface RecordProvider {
//    void setup(Project paramProject, ChatContextTag paramChatContextTag, ChatContextTagButton paramChatContextTagButton);
//
//    void click(Project paramProject, ChatContextTag paramChatContextTag, ChatContextTagButton paramChatContextTagButton);

    List<PopupRecord> listSuggestPrompts(Project paramProject, String paramString, PopupRecord parentPopupRecord);

    Icon getIconOfSuggestPrompt(Project paramProject, PopupRecord record);

//    void clickFromMarkdownPanel(Project paramProject, ChatContextTag paramChatContextTag, JComponent paramJComponent);
}
