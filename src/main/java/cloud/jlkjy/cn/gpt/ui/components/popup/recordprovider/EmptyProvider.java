package cloud.jlkjy.cn.gpt.ui.components.popup.recordprovider;


import cloud.jlkjy.cn.gpt.model.PopupRecord;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class EmptyProvider implements RecordProvider{
    @Override
    public List<PopupRecord> listSuggestPrompts(Project paramProject, String paramString, PopupRecord parentPopupRecord) {
        return Collections.emptyList();
    }

    @Override
    public Icon getIconOfSuggestPrompt(Project paramProject, PopupRecord record) {
        return null;
    }
}
