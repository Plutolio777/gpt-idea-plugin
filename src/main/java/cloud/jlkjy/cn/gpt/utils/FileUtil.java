package cloud.jlkjy.cn.gpt.utils;

import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.Arrays;
import java.util.List;

public class FileUtil {
    private static final List<String> INVALID_INDEX_DIR = Arrays.asList(new String[] { ".git", ".idea", ".m2", "node_modules" });

    public static boolean isValidDirectory(VirtualFile file) {
        if (file == null)
            return false;
        for (String ignoreDir : INVALID_INDEX_DIR) {
            if (file.getPath().contains(ignoreDir))
                return false;
        }
        return true;
    }

//    public static Boolean isValidProjectFile(Project project, VirtualFile file) {
//        if (isFileInArchive(file))
//            return Boolean.valueOf(false);
//        if (DumbService.isDumb(project))
//            return Boolean.valueOf(true);
//        return (Boolean)DumbService.getInstance(project).runReadActionInSmartMode(() -> {
//            try {
//                ProjectFileIndex projectFileIndex = ProjectFileIndex.getInstance(project);
//                if (projectFileIndex.isExcluded(file) || projectFileIndex.isInLibrary(file) || projectFileIndex.isInLibrarySource(file) || projectFileIndex.isUnderIgnored(file) || projectFileIndex.isInLibraryClasses(file))
//                    return Boolean.valueOf(false);
//            } catch (IndexNotReadyException e) {
//                log.warn("index not ready. ignore checking ignore file");
//            } catch (Exception e) {
//                log.warn("check ignore file error", e);
//            }
//            return Boolean.valueOf(true);
//        });
//    }
}
