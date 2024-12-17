package cloud.jlkjy.cn.gpt.ui.components.popup;

import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.ActiveIcon;
import com.intellij.openapi.ui.popup.IconButton;
import com.intellij.openapi.ui.popup.MaskProvider;
import com.intellij.openapi.ui.popup.MouseChecker;
import com.intellij.ui.ActiveComponent;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.popup.AbstractPopup;
import com.intellij.util.BooleanFunction;
import com.intellij.util.ui.JBUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class SuggestPromptAbstractPopup extends AbstractPopup {
  public SuggestPromptAbstractPopup() {
    setSpeedSearchAlwaysShown();
  }
  
  public SuggestPromptAbstractPopup(JComponent component, Project project, Boolean requestFocus) {
    init(project, component, null, requestFocus.booleanValue(), true, true, (String)null, false, null, null, true, 
        Collections.emptySet(), false, (ActiveComponent)null, (IconButton)null, (MouseChecker)null, true, (ActiveIcon)null, true, false, true, (Dimension)null, 0.0F, (MaskProvider)null, true, false, new Component[0], (String)null, 2, true, 
        
        Collections.emptyList(), (Component)null, null, false, true, true, (Color)null, true, (BooleanFunction)null);
  }
  
  public JBTextField getSearchField() {
    this.mySpeedSearchPatternField.getTextEditor().setFocusable(true);
    this.mySpeedSearchPatternField.setBorder(BorderFactory.createEmptyBorder());
    this.mySpeedSearchPatternField.getTextEditor().setBackground(ColorUtil.getEditorBackgroundColor());
    this.mySpeedSearchPatternField.setFont((Font)JBUI.Fonts.label().deriveFont(13.0F));
    this.mySpeedSearchPatternField.getTextEditor().setFont((Font)JBUI.Fonts.label().deriveFont(13.0F));
    return this.mySpeedSearchPatternField.getTextEditor();
  }
}