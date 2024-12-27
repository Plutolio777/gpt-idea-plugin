package cloud.jlkjy.cn.gpt.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.function.Consumer;

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

    public static Rectangle2D getStringFontSize(String text, Font font) {
        if ("".equals(text))
            return new Rectangle2D.Float(0.0F, 0.0F, 0.0F, 0.0F);
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return font.getStringBounds(text, frc);
    }

    public static void foreachChildComponent(JComponent component, Consumer<JComponent> handler) {
        LinkedList<JComponent> componentList = new LinkedList<>();
        componentList.offer(component);
        while (!componentList.isEmpty()) {
            JComponent child = componentList.poll();
            if (child != null) {
                handler.accept(child);
//                if (child instanceof IgnoreForeachChildComponent)
//                    continue;
                for (Component com : child.getComponents()) {
                    if (com instanceof JComponent)
                        componentList.offer((JComponent)com);
                }
            }
        }
    }
}
