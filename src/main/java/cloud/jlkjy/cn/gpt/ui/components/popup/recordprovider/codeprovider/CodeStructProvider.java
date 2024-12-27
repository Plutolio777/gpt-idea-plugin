package cloud.jlkjy.cn.gpt.ui.components.popup.recordprovider.codeprovider;

import cloud.jlkjy.cn.gpt.core.codestruct.CodeStructService;
import cloud.jlkjy.cn.gpt.core.codestruct.CodeStructServiceManager;

import cloud.jlkjy.cn.gpt.model.PopupRecord;
import cloud.jlkjy.cn.gpt.ui.components.popup.recordprovider.RecordProvider;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;


import javax.swing.*;
import java.util.List;

public class CodeStructProvider implements RecordProvider {


    @Override
    public List<PopupRecord> listSuggestPrompts(Project project, String query, PopupRecord parentPopupRecord) {

        CodeStructServiceManager service = ApplicationManager.getApplication().getService(CodeStructServiceManager.class);
        String path = parentPopupRecord.getHintText();
        CodeStructService codeStructService = service.getServiceByFilePath(path);
        if (codeStructService == null) {
            return List.of();
        }

        return codeStructService.getFileStruct(path, project);
    }

    @Override
    public Icon getIconOfSuggestPrompt(Project paramProject, PopupRecord record) {
        return null;
    }

}
