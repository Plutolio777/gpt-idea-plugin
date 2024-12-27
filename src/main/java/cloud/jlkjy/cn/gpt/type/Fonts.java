package cloud.jlkjy.cn.gpt.type;

import com.intellij.util.ui.JBUI;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.editor.impl.FontInfo;
import com.intellij.ide.ui.AntialiasingType;

public interface Fonts {
    Font Default = new Font("Microsoft YaHei UI", Font.PLAIN, 12);
    Font LABEL_DEFAILT_FONT = (Font)JBUI.Fonts.label(14.0F);
    Font LABEL_TAG_SMALL_FONT = (Font) JBUI.Fonts.label(12.0F);


    static FontMetrics fontMetrics(@NotNull JComponent component, @NotNull Font font) {
        FontRenderContext editorContext = FontInfo.getFontRenderContext(component);
        FontRenderContext context = new FontRenderContext(editorContext.getTransform(), AntialiasingType.getKeyForCurrentScope(false), editorContext.getFractionalMetricsHint());
        return FontInfo.getFontMetrics(font, context);
    }

    static String clipString(FontMetrics metrics, String text, int maxWidth, boolean fromTail) {
        int textLength = metrics.stringWidth(text);
        if (textLength <= maxWidth)
            return text;
        String clipString = "...";
        maxWidth -= metrics.stringWidth(clipString);
        if (maxWidth <= 0)
            return clipString;
        StringBuilder sb = new StringBuilder();
        if (fromTail) {
            for (int i = text.length() - 1; i >= 0; ) {
                int charWidth = metrics.charWidth(text.charAt(i));
                maxWidth -= charWidth;
                if (maxWidth >= 0) {
                    sb.insert(0, text.charAt(i));
                }
                i--;
            }
            if (!sb.toString().equals(text))
                sb.insert(0, clipString);
        } else {
            char[] arrayOfChar;
            int i;
            byte b;
            for (arrayOfChar = text.toCharArray(), i = arrayOfChar.length, b = 0; b < i; ) {
                char ch = arrayOfChar[b];
                int charWidth = metrics.charWidth(ch);
                maxWidth -= charWidth;
                if (maxWidth >= 0) {
                    sb.append(ch);
                }
                b++;
            }
            if (!sb.toString().equals(text))
                sb.append(clipString);
        }
        return sb.toString();
    }

     static String getStringAdaptiveWidth(JComponent component, String text, int maxWidth, boolean fromTail) {
        FontMetrics metrics = fontMetrics(component, component.getFont());
        return clipString(metrics, text, maxWidth, fromTail);
    }


}
