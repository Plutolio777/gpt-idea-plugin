package cloud.jlkjy.cn.gpt.ui.components.textpanel;

import javax.swing.SizeRequirements;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class ChatStyledEditorKit extends StyledEditorKit {
  private static final int MAX_HEIGHT = 24;
  
  public ViewFactory getViewFactory() {
    return new ChatInputStyledViewFactory();
  }
  
  static class ChatInputStyledViewFactory implements ViewFactory {
    public View create(Element elem) {
      String kind = elem.getName();
      if (kind != null) {
          switch (kind) {
              case "content" -> {
                  return new CustomLabelView(elem);
              }
              case "paragraph" -> {
                  return new CustomParagraphView(elem);
              }
              case "section" -> {
                  return new BoxView(elem, 1);
              }
              case "component" -> {
                  return new CenteredComponentView(elem);
              }
              case "icon" -> {
                  return new IconView(elem);
              }
          }
      }
      return new ChatStyledEditorKit.CustomLabelView(elem);
    }
  }
  
  static class CustomParagraphView extends ParagraphView {
    public CustomParagraphView(Element elem) {
      super(elem);
    }
    
    public float getMaximumSpan(int axis) {
      float result = super.getMaximumSpan(axis);
      if (axis == 1)
        result = Math.max(result, 24.0F); 
      return result;
    }
    
    public float getMinimumSpan(int axis) {
      float result = super.getMinimumSpan(axis);
      if (axis == 1)
        result = Math.max(result, 24.0F); 
      return result;
    }
    
    public float getPreferredSpan(int axis) {
      float result = super.getPreferredSpan(axis);
      if (axis == 1)
        result = Math.max(result, 24.0F); 
      return result;
    }
    
    protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) {
      if (r == null)
        r = new SizeRequirements(); 
      float pref = this.layoutPool.getPreferredSpan(axis);
      float min = this.layoutPool.getMinimumSpan(axis);
      r.minimum = (int)min;
      r.preferred = Math.max(r.minimum, (int)pref);
      r.maximum = Integer.MAX_VALUE;
      r.alignment = 0.5F;
      return r;
    }
    
    protected void layout(int width, int height) {
      super.layout(Math.max(width, 1), height);
    }
  }
  
  static class CenteredComponentView extends ComponentView {
    public CenteredComponentView(Element elem) {
      super(elem);
    }
    
    public float getAlignment(int axis) {
      if (axis == 1)
        return 0.5F; 
      return super.getAlignment(axis);
    }
  }
  
  static class CustomLabelView extends LabelView {
    public CustomLabelView(Element elem) {
      super(elem);
    }
    
    public float getAlignment(int axis) {
      if (axis == 1)
        return 0.55F; 
      return super.getAlignment(axis);
    }
    
    public float getMaximumSpan(int axis) {
      float result = super.getMaximumSpan(axis);
      if (axis == 1)
        result = Math.max(result, 24.0F); 
      return result;
    }
    
    public float getMinimumSpan(int axis) {
      float result = super.getMinimumSpan(axis);
      if (axis == 1) {
        result = Math.max(result, 24.0F);
      } else if (axis == 0) {
        return 0.0F;
      } 
      return result;
    }
    
    public float getPreferredSpan(int axis) {
      float result = super.getPreferredSpan(axis);
      return result;
    }
    
    public float getOriginPreferredSpan(int axis) {
      return super.getPreferredSpan(axis);
    }
  }
}