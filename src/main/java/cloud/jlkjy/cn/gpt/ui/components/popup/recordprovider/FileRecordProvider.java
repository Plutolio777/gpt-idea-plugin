package cloud.jlkjy.cn.gpt.ui.components.popup.recordprovider;

import cloud.jlkjy.cn.gpt.location.CustomBundle;
import cloud.jlkjy.cn.gpt.model.PopupRecord;
import cloud.jlkjy.cn.gpt.utils.FileUtil;
import com.intellij.ide.IconProvider;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileChooser.FileElement;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.fileEditor.impl.EditorHistoryManager;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.diagnostic.Logger;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.stream.Collectors;

public class FileRecordProvider implements RecordProvider{

    private static final Logger LOG = Logger.getInstance(FileRecordProvider.class);

    public static Icon getFileTypeIcon(@Nullable Project project, String filePath) {
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(filePath);
        if (virtualFile == null)
            return null;
        FileTypeManager fileTypeManager = FileTypeManager.getInstance();
        FileType fileType = fileTypeManager.getFileTypeByFile(virtualFile);
        Icon icon = null;
        if (project != null)
            try {
                PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
                for (IconProvider iconProvider : IconProvider.EXTENSION_POINT_NAME.getExtensionList()) {
                    icon = iconProvider.getIcon((PsiElement)psiFile, 0);
                    if (icon != null)
                        break;
                }
            } catch (Exception exception) {}
        if (icon == null)
            icon = fileType.getIcon();
        return icon;
    }

    @Override
    public List<PopupRecord> listSuggestPrompts(Project project, String query, PopupRecord parentPopupRecord) {
        List<VirtualFile> fileList = listAllFiles(project, query, 20);
        if (CollectionUtils.isEmpty(fileList))
            return new ArrayList<>();
//        PopupRecord fileSuggestPrompt = (PopupRecord)InputConstants.SCOPE_SUGGEST_PROMPT_MAP.get(ChatContextTypeEnum.FILE.getType());
        List<PopupRecord> suggestPrompts = new ArrayList<>();
        for (VirtualFile file : fileList) {
            String message = CustomBundle.message("ui.chat.popup.text.select.after.add", new Object[0]);
            PopupRecord record = PopupRecord.builder().enabled(true).text(file.getName()).hintText(file.getPath()).selectHintText(message).build();
            record.setIcon(this.getIconOfSuggestPrompt(project, record));
            suggestPrompts.add(record);
        }
        return suggestPrompts;
    }

    public Icon getIconOfSuggestPrompt(Project project, PopupRecord record) {
        RunnableFuture<Icon> task = new FutureTask<>(() -> getFileTypeIcon(project, record.getHintText()));
        ApplicationManager.getApplication().runReadAction(task);
        Icon icon = null;
        try {
            icon = task.get();
        } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
            LOG.warn("Get file icon failed", e);
        }
        return icon;
    }


    private List<VirtualFile> listAllFiles(@NotNull Project project, String query, int fileCount) {
        List<VirtualFile> fileList = new ArrayList<>();
        if (project.getBasePath() == null) {
            return Collections.emptyList();
        }
        VirtualFile rootDir = LocalFileSystem.getInstance().findFileByPath(project.getBasePath());
        if (rootDir == null || !rootDir.exists())
            return fileList;
        // 添加当前选中文件
        VirtualFile currentFile = getCurrentFile(project, query);
        if (currentFile != null)
            fileList.add(currentFile);
        if (fileList.size() > fileCount)
            return fileList;
        // 获取当前打开窗口的文件
        List<VirtualFile> openedFiles = listOpenedFiles(project, query);
        Objects.requireNonNull(openedFiles);
        // 去除重复文件
        fileList.forEach(openedFiles::remove);
        fileList.addAll(openedFiles);
        if (fileList.size() > fileCount) {
            fileList = fileList.subList(0, fileCount);
            return fileList;
        }

        // 获取最近打开文件
        List<VirtualFile> recentFiles = listRecentFiles(project, query);
        Objects.requireNonNull(recentFiles);
        fileList.forEach(recentFiles::remove);
        fileList.addAll(recentFiles);
        if (fileList.size() > fileCount) {
            fileList = fileList.subList(0, fileCount);
            return fileList;
        }
        // 如果还是不够则遍历跟目录依次添加文件
        List<VirtualFile> childFiles = new ArrayList<>();
        childFiles = searchFiles(project, query, rootDir, fileCount);
        Objects.requireNonNull(childFiles);
        fileList.forEach(childFiles::remove);
        fileList.addAll(childFiles);
        if (fileList.size() > fileCount) {
            fileList = fileList.subList(0, fileCount);
            return fileList;
        }
        return fileList;
    }

    private List<VirtualFile> listRecentFiles(Project project, String query) {
        List<VirtualFile> historyFiles = EditorHistoryManager.getInstance(project).getFileList();
        historyFiles = filterFilesByQuery(project, historyFiles, query);
        return historyFiles;
    }

    private VirtualFile getCurrentFile(Project project, String query) {
        VirtualFile[] selectedFiles = FileEditorManager.getInstance(project).getSelectedFiles();
        if (selectedFiles.length > 0) {
            List<VirtualFile> selectFileList = new ArrayList<>(Arrays.asList(selectedFiles));
            selectFileList = filterFilesByQuery(project, selectFileList, query);
            if (!selectFileList.isEmpty())
                return selectedFiles[0];
        }
        return null;
    }

    private List<VirtualFile> listOpenedFiles(Project project, String query) {
        VirtualFile[] openFilesArray = FileEditorManager.getInstance(project).getOpenFiles();
        List<VirtualFile> openFiles = new ArrayList<>(Arrays.asList(openFilesArray));
        openFiles = filterFilesByQuery(project, openFiles, query);
        return openFiles;
    }

    private List<VirtualFile> filterFilesByQuery(Project project, List<VirtualFile> files, String query) {
        if (CollectionUtils.isEmpty(files))
            return files;
        files = (List<VirtualFile>)files.stream().filter(virtualFile -> query == null || query.isEmpty() || virtualFile.getName().toLowerCase().contains(query)).collect(Collectors.toList());
        return files;
    }


    private List<VirtualFile> searchFiles(Project project, String query, VirtualFile root, int fileCount) {
        List<VirtualFile> fileList = new ArrayList<>();
        long start = System.currentTimeMillis();
        Stack<VirtualFile> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty() && fileList.size() < fileCount) {
            VirtualFile currentDir = stack.pop();
            VirtualFile[] childList = currentDir.getChildren();
            if (childList != null) {
                childList = Arrays.<VirtualFile>copyOfRange(childList, 0, Math.min(100, childList.length));
                for (VirtualFile child : childList) {
                    if (fileList.size() >= fileCount)
                        break;
                    if (!FileElement.isFileHidden(child)) {
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - start > 1000L) {
//                            LOG.warn("too many files timeout, query is " + query);
                            return fileList;
                        }
                        if (child.isDirectory()) {
                            if (FileUtil.isValidDirectory(child))
                                stack.push(child);
                        } else {
                            List<VirtualFile> childFiles = new ArrayList<>(Arrays.asList(new VirtualFile[] { child }));
                            fileList.addAll(filterFilesByQuery(project, childFiles, query));
                        }
                    }
                }
            }
        }
        return fileList;
    }
}
