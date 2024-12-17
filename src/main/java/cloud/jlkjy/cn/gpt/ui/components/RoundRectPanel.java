package cloud.jlkjy.cn.gpt.ui.components;

import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.ui.JBColor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class RoundRectPanel extends JPanel implements RefreshColorComponent {
  private static final int DEFAULT_CORNER_RADIUS = 16;

  private int cornerRadius = 16;

  private Color borderColor = null;

  private RoundLineBorder roundLineBorder;

  public RoundRectPanel(LayoutManager layout, boolean isDoubleBuffered) {
    super(layout, isDoubleBuffered);
  }

  public RoundRectPanel(LayoutManager layout) {
    super(layout);
  }

  public RoundRectPanel(boolean isDoubleBuffered) {
    super(isDoubleBuffered);
  }

  public RoundRectPanel() {}

  protected void paintComponent(Graphics g) {
    Dimension arcs = new Dimension(this.cornerRadius, this.cornerRadius);
    int width = getWidth();
    int height = getHeight();
    Graphics2D graphics = (Graphics2D)g;
    graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.setPaint(getBackground());
    graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
    paintChildren(graphics);
    if (this.roundLineBorder != null)
      this.roundLineBorder.paintBorder(this, graphics, 0, 0, width, height);
    graphics.dispose();
  }

  public int getCornerRadius() {
    return this.cornerRadius;
  }

  public void setCornerRadius(int cornerRadius) {
    this.cornerRadius = cornerRadius;
  }

  public Color getBorderColor() {
    return this.borderColor;
  }

  public void setBorderColor(Color borderColor) {
    this.borderColor = borderColor;
    if (borderColor != null) {
      this.roundLineBorder = new RoundLineBorder(borderColor, 2, this.cornerRadius);
    } else {
      this.roundLineBorder = null;
    }
  }

  public void refreshColor(EditorColorsScheme scheme, Color themeColor) {
    if (!(getBackground() instanceof JBColor))
      return;
    JBColor jbColor = (JBColor)getBackground();
    if (themeColor == null) {
      JBColor.setDark(true);
    } else {
      JBColor.setDark(false);
    }
  }
}
