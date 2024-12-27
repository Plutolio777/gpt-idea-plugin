package cloud.jlkjy.cn.gpt.location;

import com.intellij.DynamicBundle;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public class CustomBundle extends DynamicBundle {

    private static CustomBundle instance = new CustomBundle();
    public CustomBundle() {
        super("messageBundle");
    }


    public static String message(@NotNull @PropertyKey(resourceBundle = "messageBundle") String key, Object... params) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(params, "params");

//        String fullName = instance.getMessage("cosy.plugin.name", new Object[0]);
//        String simpleName = instance.getMessage("cosy.plugin.simple.name", new Object[0]);
        return instance.getMessage(key, params);
    }
}
