package cloud.jlkjy.cn.gpt.ui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.LineBorder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RoundLineBorder extends LineBorder {
  private final int radius;
  
  public RoundLineBorder(@NotNull Color color, int thickness, int radius) {
    super(color, thickness, true);
    this.radius = radius;
  }
  
  public void paintBorder(@Nullable Component component, @Nullable Graphics g, int x, int y, int width, int height) {
    if (this.thickness > 0 && g instanceof Graphics2D) {
      Graphics2D graphics = (Graphics2D)g;
      Color oldColor = graphics.getColor();
      graphics.setColor(this.lineColor);
      int offs = this.thickness;
      int size = offs + offs;
      float arc = this.radius;
      Shape outer = new RoundRectangle2D.Float(x, y, width, height, arc, arc);
      Shape inner = new RoundRectangle2D.Float((x + offs), (y + offs), (width - size), (height - size), arc - offs, arc - offs);
      Path2D path = new Path2D.Float(0);
      path.append(outer, false);
      path.append(inner, false);
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      graphics.fill(path);
      graphics.setColor(oldColor);
    } 
  }
}