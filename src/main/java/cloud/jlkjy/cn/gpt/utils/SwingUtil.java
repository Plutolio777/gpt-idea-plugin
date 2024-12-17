package cloud.jlkjy.cn.gpt.utils;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.intellij.openapi.diagnostic.Logger;
public class SwingUtil {
    private static final Logger logger = Logger.getInstance(SwingUtil.class);


    public static void preventRecursiveBackgroundUpdateOnToolwindow(JComponent component) {
        try {
            Class<?> clazz = Class.forName("com.intellij.toolWindow.InternalDecoratorImpl");
            Field companionField = clazz.getDeclaredField("Companion");
            Object companionObject = companionField.get(null);
            Class<?> companionClass = companionObject.getClass();
            Method method = companionClass.getDeclaredMethod("preventRecursiveBackgroundUpdateOnToolwindow", new Class[] { JComponent.class });
            method.invoke(companionObject, new Object[] { component });
        } catch (Exception e) {
            logger.warn("preventRecursiveBackgroundUpdateOnToolwindow failed:" + e.getMessage());
        }
    }
}
