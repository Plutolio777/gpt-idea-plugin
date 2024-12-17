package cloud.jlkjy.cn.gpt.ui.components;

import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.border.Border;

public class TabLabel extends FixedHeightLabel {
  private Border externBorder;
  
  public TabLabel(String text, Icon icon, int horizontalAlignment, int fixHeight) {
    super(text, icon, horizontalAlignment, fixHeight);
  }
  
  public TabLabel(String text, int horizontalAlignment, int fixHeight) {
    super(text, horizontalAlignment, fixHeight);
  }
  
  public TabLabel(String text, int fixHeight) {
    super(text, fixHeight);
  }
  
  public TabLabel(Icon image, int horizontalAlignment, int fixHeight) {
    super(image, horizontalAlignment, fixHeight);
  }
  
  public TabLabel(Icon image, int fixHeight) {
    super(image, fixHeight);
  }
  
  public TabLabel(int fixHeight) {
    super(fixHeight);
  }
  
  protected void paintBorder(Graphics g) {
    super.paintBorder(g);
    if (this.externBorder != null)
      this.externBorder.paintBorder(this, g, 0, 0, getWidth(), getHeight()); 
  }
  
  public Border getExternBorder() {
    return this.externBorder;
  }
  
  public void setExternBorder(Border externBorder) {
    this.externBorder = externBorder;
    invalidate();
    repaint();
  }
}


/* Location:              C:\Users\liuyijun\Downloads\tongyi-jetbrains-1.4.13\tongyi-jetbrains\lib\cosy-intellij-1.4.13.jar!\com\alibabacloud\intellij\cos\\ui\search\component\TabLabel.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */