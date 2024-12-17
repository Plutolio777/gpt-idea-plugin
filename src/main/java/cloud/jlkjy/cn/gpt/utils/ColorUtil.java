package cloud.jlkjy.cn.gpt.utils;

import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
import java.awt.Color;
import javax.swing.UIManager;
import org.jetbrains.annotations.NotNull;

public class ColorUtil {
  public static Color getEditorBackgroundColor() {
    return (Color)new JBColor(() -> {
          EditorColorsManager colorsManager = EditorColorsManager.getInstance();
          EditorColorsScheme colorsScheme = colorsManager.getGlobalScheme();
          return colorsScheme.getDefaultBackground();
        });
  }
  
  public static Color getChatCardBackgroundColor() {
    Color newNotificationColor = UIManager.getColor("NotificationsToolwindow.newNotification.background");
    if (newNotificationColor != null)
      return (Color)JBColor.namedColor("NotificationsToolwindow.newNotification.background", newNotificationColor); 
    return (Color)new JBColor(() -> {
          Color color = getToolWindowBackgroundColor();
          float[] hsl = new float[3];
          Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsl);
          Color schemaColor = EditorColorsManager.getInstance().getGlobalScheme().getColor(EditorColors.READONLY_FRAGMENT_BACKGROUND_COLOR);
          if (schemaColor == null) {
            float newLight = ((int)(hsl[2] * 100.0F) - 3) / 100.0F;
            if (newLight < 0.0F)
              newLight = ((int)(hsl[2] * 100.0F) + 3) / 100.0F; 
            hsl[2] = newLight;
          } else {
            float newLight = ((int)(hsl[2] * 100.0F) + 3) / 100.0F;
            if (newLight > 1.0F)
              newLight = ((int)(hsl[2] * 100.0F) - 3) / 100.0F; 
            hsl[2] = newLight;
          } 
          return Color.getHSBColor(hsl[0], hsl[1], hsl[2]);
        });
  }
  
  public static Color getTabbedUnderlineColor() {
    return (Color)JBColor.namedColor("TabbedPane.underlineColor", (Color)new JBColor(new Color(255, 0, 0), new Color(238, 0, 0)));
  }
  
  public static Color getTabbedHoverBgColor() {
    return (Color)JBColor.namedColor("TabbedPane.hoverColor", (Color)new JBColor(new Color(255, 0, 0), new Color(238, 0, 0)));
  }
  
  public static Color getTabbedBgColor() {
    return (Color)JBColor.namedColor("TabbedPane.background", (Color)new JBColor(new Color(255, 0, 0), new Color(238, 0, 0)));
  }
  
  public static Color getTabbedSelectForegroundColor() {
    return (Color)JBColor.namedColor("TabbedPane.selectedTabTitleNormalColor", (Color)new JBColor(new Color(255, 0, 0), new Color(238, 0, 0)));
  }
  
  public static Color getChatCardChildBackgroundColor() {
    return getChatCardBackgroundColor();
  }
  
  public static Color getSelectedListItemColor() {
    return UIUtil.getListSelectionForeground(true);
  }
  
  public static Color getToolWindowBackgroundColor() {
    return (Color)JBColor.namedColor("ToolWindow.background", UIUtil.getPanelBackground());
  }
  
  public static Color getButtonHoverBackgroundColor() {
    return (Color)JBColor.namedColor("ActionButton.hoverBackground", (Color)new JBColor(new Color(255, 0, 0), new Color(238, 0, 0)));
  }
  
  public static Color getDisabledTextFieldBackgroundColor() {
    return (Color)JBColor.namedColor("TextField.disabledBackground", (Color)new JBColor((Color)Gray._242, (Color)Gray._242));
  }
  
  public static Color getEnabledTextFieldBackgroundColor() {
    return (Color)JBColor.namedColor("TextField.background", (Color)new JBColor(new Color(69, 73, 74), new Color(69, 73, 74)));
  }
  
  public static Color getTextForegroundColor() {
    return (Color)new JBColor(UIUtil::getTextAreaForeground);
  }
  
  public static Color getTextPaneForegroundColor() {
    return (Color)JBColor.namedColor("TextPane.foreground", getTextForegroundColor());
  }
  
  public static Color getLabelForegroundColor() {
    return (Color)JBColor.namedColor("Label.foreground", getTextPaneForegroundColor());
  }
  
  public static Color getAuxiliaryForegroundColor() {
    return (Color)JBColor.namedColor("Label.infoForeground", 
        (Color)JBColor.namedColor("ToolTip.infoForeground", (Color)new JBColor(new Color(255, 0, 0), new Color(238, 0, 0))));
  }
  
  public static Color getInactiveForegroundColor() {
    return (Color)JBColor.namedColor("TextArea.inactiveForeground", (Color)new JBColor(new Color(255, 0, 0), new Color(238, 0, 0)));
  }
  
  public static Color getLinkForegroundColor() {
    return (Color)JBColor.namedColor("Hyperlink.linkColor", (Color)new JBColor(new Color(255, 0, 0), new Color(238, 0, 0)));
  }
  
  public static Color getItemSelectionBackground() {
    return (Color)new JBColor(() -> UIUtil.getListSelectionBackground(true));
  }
  
  public static Color getListBackground() {
    return (Color)new JBColor(UIUtil::getListBackground);
  }
  
  public static Color getListForeground() {
    return (Color)new JBColor(UIUtil::getListForeground);
  }
  
  public static Color getPopupMenuSelectionBackground() {
    return (Color)JBColor.namedColor("PopupMenu.selectionBackground", getItemSelectionBackground());
  }
  
  @NotNull
  public static Color getListHoverBackground() {
      return JBColor.namedColor("List.hoverBackground", (Color) new JBColor(15595004, 4606541));
  }
}


/* Location:              C:\Users\liuyijun\Downloads\tongyi-jetbrains-1.4.13\tongyi-jetbrains\lib\cosy-intellij-1.4.13.jar!\com\alibabacloud\intellij\cos\\util\ColorUtil.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */