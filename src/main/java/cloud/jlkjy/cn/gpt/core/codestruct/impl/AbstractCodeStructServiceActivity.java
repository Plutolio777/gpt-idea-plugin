package cloud.jlkjy.cn.gpt.core.codestruct.impl;

import cloud.jlkjy.cn.gpt.model.PopupRecord;
import com.intellij.ide.structureView.StructureView;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.project.impl.ProjectLifecycleListener;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public abstract class AbstractCodeStructServiceActivity extends AbstractCodeStructService implements ProjectManagerListener {

    public AbstractCodeStructServiceActivity(String type) {
        super(type);
    }


    @Nullable
    public StructureViewTreeElement getStructureViewTreeElement(@NotNull String path, @NotNull Project project) {
        return ApplicationManager.getApplication().runReadAction((Computable<StructureViewTreeElement>) () -> {
            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(path);
            if (virtualFile == null) {
                return null;
            }

            FileEditor fileEditor = FileEditorManager.getInstance(project).getSelectedEditor(virtualFile);
            FileType fileType = FileTypeManager.getInstance().getFileTypeByFile(virtualFile);
            StructureViewBuilder structureViewBuilder = StructureViewBuilder.PROVIDER.getStructureViewBuilder(fileType, virtualFile, project);

            if (structureViewBuilder == null) {
                return null;
            }

            StructureView structureView = structureViewBuilder.createStructureView(fileEditor, project);
            StructureViewModel treeModel = structureView.getTreeModel();

            return treeModel.getRoot();
        });
    }
}
