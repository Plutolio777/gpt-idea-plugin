package cloud.jlkjy.cn.gpt.layout;

import com.intellij.vcs.log.ui.frame.WrappedFlowLayout;

import javax.swing.*;
import java.awt.*;

public class ChatContextTagLayout extends WrappedFlowLayout {
  private int minRowHeight = 0;
  
  public ChatContextTagLayout(int hGap, int vGap) {
    super(hGap, vGap);
  }
  
  public ChatContextTagLayout(int hGap, int vGap, int minRowHeight) {
    super(hGap, vGap);
    this.minRowHeight = minRowHeight;
  }
  
  public Dimension getDimension(Container target, int maxWidth) {
    Insets insets = target.getInsets();
    int height = insets.top + insets.bottom;
    int width = insets.left + insets.right;
    int rowHeight = 0;
    int rowWidth = insets.left + insets.right;
    boolean isVisible = false;
    boolean start = true;
    boolean first = true;
    synchronized (target.getTreeLock()) {
      for (int i = 0; i < target.getComponentCount(); i++) {
        Component component = target.getComponent(i);
        if (component.isVisible()) {
          isVisible = true;
          Dimension size = component.getPreferredSize();
          if (!first) {
            rowWidth += getHgap();
          } else {
            first = false;
          } 
          int newWidth = size.width;
          if (component instanceof JTextArea && ((JTextArea)component).getLineCount() > 1)
            newWidth = maxWidth - rowWidth; 
          if (rowWidth + newWidth >= maxWidth && !start) {
            height += getVgap() + rowHeight;
            width = Math.max(width, rowWidth);
            rowWidth = insets.left + insets.right;
            rowHeight = 0;
            first = true;
          } 
          rowHeight = Math.max(rowHeight, size.height);
          rowHeight = Math.max(rowHeight, this.minRowHeight);
          rowWidth += newWidth;
          start = false;
        } 
      } 
      height += rowHeight;
      width = Math.max(width, rowWidth);
      Dimension dim = null;
      dim = new Dimension(width, height);
      return dim;
    } 
  }
  
  public Dimension getWrappedSize(Container target) {
    Container parent = SwingUtilities.getUnwrappedParent(target);
    int maxWidth = parent.getWidth() - (parent.getInsets()).left + (parent.getInsets()).right;
    return getDimension(target, maxWidth);
  }
  
  private int moveComponents(Container target, int x, int y, int width, int height, int rowStart, int rowEnd, boolean ltr, boolean useBaseline, int[] ascent, int[] descent) {
    switch (getAlignment()) {
      case FlowLayout.LEFT:
        x += ltr ? 0 : width;
        break;
      case FlowLayout.CENTER:
        x += width / 2;
        break;
      case FlowLayout.RIGHT:
        x += ltr ? width : 0;
        break;
      case FlowLayout.TRAILING:
        x += width;
        break;
    } 
    int maxAscent = 0;
    int nonbaselineHeight = 0;
    int baselineOffset = 0;
    if (useBaseline) {
      int maxDescent = 0;
      for (int j = rowStart; j < rowEnd; j++) {
        Component m = target.getComponent(j);
        if (m.isVisible())
          if (ascent[j] >= 0) {
            maxAscent = Math.max(maxAscent, ascent[j]);
            maxDescent = Math.max(maxDescent, descent[j]);
          } else {
            nonbaselineHeight = Math.max(m.getHeight(), nonbaselineHeight);
          }  
      } 
      height = Math.max(maxAscent + maxDescent, nonbaselineHeight);
      baselineOffset = (height - maxAscent - maxDescent) / 2;
    } 
    for (int i = rowStart; i < rowEnd; i++) {
      Component m = target.getComponent(i);
      if (m.isVisible()) {
        int cy;
        if (useBaseline && ascent[i] >= 0) {
          cy = y + baselineOffset + maxAscent - ascent[i];
        } else {
          cy = y + (height - m.getHeight()) / 2;
        } 
        if (ltr) {
          m.setLocation(x, cy);
        } else {
          m.setLocation(target.getWidth() - x - m.getWidth(), cy);
        } 
        x += m.getWidth() + getHgap();
      } 
    } 
    return height;
  }
  
  public void layoutContainer(Container target) {
    synchronized (target.getTreeLock()) {
      Insets insets = target.getInsets();
      int maxwidth = target.getWidth() - insets.left + insets.right;
      int nmembers = target.getComponentCount();
      int x = 0, y = insets.top;
      int rowh = 0, start = 0;
      boolean ltr = target.getComponentOrientation().isLeftToRight();
      boolean useBaseline = getAlignOnBaseline();
      int[] ascent = null;
      int[] descent = null;
      if (useBaseline) {
        ascent = new int[nmembers];
        descent = new int[nmembers];
      } 
      for (int i = 0; i < nmembers; i++) {
        Component m = target.getComponent(i);
        if (m.isVisible()) {
          Dimension d = m.getPreferredSize();
          int originWidth = d.width;
          if (d.width >= maxwidth - x - getHgap()) {
            d.width = Math.min(maxwidth, d.width);
            originWidth = d.width;
          } 
          m.setSize(d.width, d.height);
          if (useBaseline) {
            int baseline = m.getBaseline(d.width, d.height);
            if (baseline >= 0) {
              ascent[i] = baseline;
              descent[i] = d.height - baseline;
            } else {
              ascent[i] = -1;
            } 
          } 
          if (x > 0)
            x += getHgap(); 
          if (x == 0 || x + originWidth <= maxwidth) {
            x += d.width;
            rowh = Math.max(rowh, d.height);
            rowh = Math.max(rowh, this.minRowHeight);
          } else {
            rowh = moveComponents(target, insets.left, y, maxwidth - x, rowh, start, i, ltr, useBaseline, ascent, descent);
            x = d.width;
            y += getVgap() + rowh;
            rowh = d.height;
            rowh = Math.max(rowh, this.minRowHeight);
            start = i;
          } 
        } 
      } 
      moveComponents(target, insets.left, y, maxwidth - x, rowh, start, nmembers, ltr, useBaseline, ascent, descent);
    } 
  }
}