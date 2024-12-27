package cloud.jlkjy.cn.gpt.core.codestruct.impl;

import cloud.jlkjy.cn.gpt.core.codestruct.CodeStructService;
import cloud.jlkjy.cn.gpt.core.codestruct.CodeStructServiceManager;
import cloud.jlkjy.cn.gpt.model.PopupRecord;
import com.intellij.ide.structureView.StructureView;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractCodeStructService implements CodeStructService {
    private static final Logger LOGGER = Logger.getInstance(AbstractCodeStructService.class);
    public String type;

    public AbstractCodeStructService(String type) {
        this.type = type;
        registerCodeService();
        LOGGER.info(String.format("code struct %s already registered", type));
    }

    public abstract List<PopupRecord> getFileStruct(String path, @NotNull Project project);

    public void registerCodeService() {
        CodeStructServiceManager service = ApplicationManager.getApplication().getService(CodeStructServiceManager.class);
        service.registerServices(this.type, this);
    }

}
