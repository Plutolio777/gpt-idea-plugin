package cloud.jlkjy.cn.gpt.core.codestruct;

import cloud.jlkjy.cn.gpt.model.PopupRecord;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CodeStructService {
    public List<PopupRecord> getFileStruct(String path, @NotNull Project project);

}
