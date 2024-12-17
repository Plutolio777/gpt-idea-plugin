package cloud.jlkjy.cn.gpt.ui.components;


import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import com.intellij.util.ui.JBUI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;

public class DisplayIcon extends JLabel {
  public static final int HOVER_BG_STYLE = 0;
  
  public static final int HOVER_SILENT_STYLE = 1;
  
  Color background;
  
  int style;
  
  public DisplayIcon(Icon icon) {
    this(icon, 0);
  }
  
  public DisplayIcon(Icon icon, final int style) {
    super(icon, SwingConstants.CENTER);
    this.style = style;
    this.background = getBackground();
    setBorder((Border)JBUI.Borders.empty());
    setPreferredSize(new Dimension(20, 20));
    addMouseListener(new MouseAdapter() {
          public void mouseEntered(MouseEvent e) {
            if (DisplayIcon.this.isEnabled() && style == 0) {
              DisplayIcon.this.setOpaque(true);
              DisplayIcon.this.setBackground(ColorUtil.getButtonHoverBackgroundColor());
              DisplayIcon.this.invalidate();
              DisplayIcon.this.repaint();
            } 
          }
          
          public void mouseExited(MouseEvent e) {
            if (DisplayIcon.this.isEnabled() && style == 0) {
              DisplayIcon.this.setOpaque(false);
              DisplayIcon.this.setBackground(DisplayIcon.this.background);
              DisplayIcon.this.invalidate();
              DisplayIcon.this.repaint();
            } 
          }
          
          public void mousePressed(MouseEvent e) {
            if (DisplayIcon.this.isEnabled() && style == 0) {
              DisplayIcon.this.setOpaque(true);
              DisplayIcon.this.setBackground(ColorUtil.getItemSelectionBackground());
              DisplayIcon.this.invalidate();
              DisplayIcon.this.repaint();
            } 
          }
          
          public void mouseReleased(MouseEvent e) {
            if (DisplayIcon.this.isEnabled() && style == 0) {
              DisplayIcon.this.setOpaque(false);
              DisplayIcon.this.setBackground(DisplayIcon.this.background);
              DisplayIcon.this.invalidate();
              DisplayIcon.this.repaint();
            } 
          }
        });
    if (isEnabled())
      setCursor(Cursor.getPredefinedCursor(12)); 
  }
  
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    if (enabled) {
      setCursor(Cursor.getPredefinedCursor(12));
    } else {
      setCursor(Cursor.getPredefinedCursor(0));
    } 
  }
  
  public void selected() {
    setEnabled(false);
    setOpaque(true);
    setBackground(ColorUtil.getButtonHoverBackgroundColor());
    invalidate();
    repaint();
  }
  
  public void unSelected() {
    setEnabled(false);
    setOpaque(false);
    setBackground(this.background);
    invalidate();
    repaint();
  }
  
  public void setBackground(Color bg) {
    super.setBackground(bg);
    this.background = bg;
  }
  
  protected void paintComponent(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    Graphics2D graphics = (Graphics2D)g;
    if (isOpaque()) {
      Dimension arcs = new Dimension(8, 8);
      graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setPaint(getBackground());
      graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    } 
    int iconX = (width - getIcon().getIconWidth()) / 2;
    int iconY = (height - getIcon().getIconHeight()) / 2;
    getIcon().paintIcon(this, graphics, iconX, iconY);
    graphics.dispose();
  }
}
