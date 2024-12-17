package cloud.jlkjy.cn.gpt.ui.components;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JLabel;

public class FixedHeightLabel extends JLabel {
  int fixHeight;
  
  public FixedHeightLabel(String text, Icon icon, int horizontalAlignment, int fixHeight) {
    super(text, icon, horizontalAlignment);
    this.fixHeight = fixHeight;
    setMinimumSize(new Dimension(2147483647, fixHeight));
    setMaximumSize(new Dimension(2147483647, fixHeight));
  }
  
  public FixedHeightLabel(String text, int horizontalAlignment, int fixHeight) {
    super(text, horizontalAlignment);
    this.fixHeight = fixHeight;
    setMinimumSize(new Dimension(2147483647, fixHeight));
    setMaximumSize(new Dimension(2147483647, fixHeight));
  }
  
  public FixedHeightLabel(String text, int fixHeight) {
    super(text);
    this.fixHeight = fixHeight;
    setMinimumSize(new Dimension(2147483647, fixHeight));
    setMaximumSize(new Dimension(2147483647, fixHeight));
  }
  
  public FixedHeightLabel(Icon image, int horizontalAlignment, int fixHeight) {
    super(image, horizontalAlignment);
    this.fixHeight = fixHeight;
    setMinimumSize(new Dimension(2147483647, fixHeight));
    setMaximumSize(new Dimension(2147483647, fixHeight));
  }
  
  public FixedHeightLabel(Icon image, int fixHeight) {
    super(image);
    this.fixHeight = fixHeight;
    setMinimumSize(new Dimension(2147483647, fixHeight));
    setMaximumSize(new Dimension(2147483647, fixHeight));
  }
  
  public FixedHeightLabel(int fixHeight) {
    this.fixHeight = fixHeight;
    setMinimumSize(new Dimension(2147483647, fixHeight));
    setMaximumSize(new Dimension(2147483647, fixHeight));
  }
  
  public Dimension getPreferredSize() {
    Dimension dimension = super.getPreferredSize();
    dimension.height = this.fixHeight;
    return dimension;
  }
}


/* Location:              C:\Users\liuyijun\Downloads\tongyi-jetbrains-1.4.13\tongyi-jetbrains\lib\cosy-intellij-1.4.13.jar!\com\alibabacloud\intellij\cos\\ui\search\component\FixedHeightLabel.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */