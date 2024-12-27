package cloud.jlkjy.cn.gpt.core.codestruct;

import java.util.HashMap;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import org.jetbrains.annotations.Nullable;

public class CodeStructServiceManager {

    private static final Logger LOGGER = Logger.getInstance(CodeStructServiceManager.class);
    private final HashMap<String, CodeStructService> ServiceRegistry;

    public CodeStructServiceManager() {
        this.ServiceRegistry = new HashMap<>();
        LOGGER.info("code struct create success");
        System.out.println(2222);
    }

    public void registerServices(String index, CodeStructService services) {
        System.out.println(333);
        this.ServiceRegistry.put(index, services);
    }

    public CodeStructService getServiceById(String id) {
        return this.ServiceRegistry.get(id);
    }

    public boolean canBeParseToStruct(String path) {
        FileType type = FileTypeManager.getInstance().getFileTypeByFileName(path);
        String index = type.getName();
        return this.ServiceRegistry.containsKey(index);
    }

    @Nullable
    public CodeStructService getServiceByFilePath(String path) {
        FileType type = FileTypeManager.getInstance().getFileTypeByFileName(path);
        String index = type.getName();
        return this.getServiceById(index);
    }

}
