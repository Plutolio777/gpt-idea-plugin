package cloud.jlkjy.cn.gpt.utils;

import com.intellij.openapi.ui.popup.JBPopup;
import javax.swing.JList;

public class PopUtil {
  public static boolean isPopUsable(JBPopup jbPopup) {
      return jbPopup != null && !jbPopup.isDisposed() && jbPopup.isVisible();
  }
  
  public static boolean isNotNullJList(JList jList) {
      return jList != null && jList.getModel().getSize() != 0 && jList.isVisible();
  }
}