package cloud.jlkjy.cn.gpt.ui.components;

import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.ui.JBColor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.function.Consumer;
import javax.swing.*;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RichRoundRectPanel extends JPanel implements RefreshColorComponent {
    private static final int DEFAULT_CORNER_RADIUS = 16;


    private int cornerRadius = 16;


    private Color borderColor = null;

    private RoundLineBorder roundLineBorder;

    private Color defaultBackgoundColor;

    private Color hoverBackgoundColor;

    private Color selectedBackgoundColor;

    private boolean disableBackgroundColorEvent = false;

    public RichRoundRectPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public RichRoundRectPanel(LayoutManager layout) {
        super(layout);
    }

    public RichRoundRectPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public RichRoundRectPanel() {
        setup();
    }

    public void setup() {
        foreachChildComponent(this, this::setupComponent);
    }

    public void setupComponent(final Component component) {
        component.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (RichRoundRectPanel.this.hoverBackgoundColor != null && component.isEnabled() && !RichRoundRectPanel.this.disableBackgroundColorEvent)
                    RichRoundRectPanel.this.setBackground(RichRoundRectPanel.this.hoverBackgoundColor);
            }

            public void mouseExited(MouseEvent e) {
                if (RichRoundRectPanel.this.defaultBackgoundColor != null && component.isEnabled() && !RichRoundRectPanel.this.disableBackgroundColorEvent)
                    RichRoundRectPanel.this.setBackground(RichRoundRectPanel.this.defaultBackgoundColor);
            }

            public void mouseReleased(MouseEvent e) {
                if (RichRoundRectPanel.this.defaultBackgoundColor != null && component.isEnabled() && !RichRoundRectPanel.this.disableBackgroundColorEvent)
                    RichRoundRectPanel.this.setBackground(RichRoundRectPanel.this.defaultBackgoundColor);
            }

            public void mousePressed(MouseEvent e) {
                if (RichRoundRectPanel.this.selectedBackgoundColor != null && component.isEnabled() && !RichRoundRectPanel.this.disableBackgroundColorEvent)
                    RichRoundRectPanel.this.setBackground(RichRoundRectPanel.this.selectedBackgoundColor);
            }
        });
        component.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (RichRoundRectPanel.this.hoverBackgoundColor != null && component.isEnabled() && !RichRoundRectPanel.this.disableBackgroundColorEvent)
                    RichRoundRectPanel.this.setBackground(RichRoundRectPanel.this.hoverBackgoundColor);
            }

            public void focusLost(FocusEvent e) {
                if (RichRoundRectPanel.this.defaultBackgoundColor != null && component.isEnabled() && !RichRoundRectPanel.this.disableBackgroundColorEvent)
                    RichRoundRectPanel.this.setBackground(RichRoundRectPanel.this.defaultBackgoundColor);
            }
        });
    }

    public void paint(Graphics g) {
        Dimension arcs = new Dimension(this.cornerRadius, this.cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setPaint(getBackground());
        graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
        paintChildren(graphics);
        if (this.roundLineBorder != null)
            this.roundLineBorder.paintBorder(this, graphics, 0, 0, width, height);
        graphics.dispose();
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
        JBColor jbColor = (JBColor) getBackground();
        if (themeColor == null) {
            JBColor.setDark(true);
        } else {
            JBColor.setDark(false);
        }
    }

    public static void foreachChildComponent(JComponent component, Consumer<JComponent> handler) {
        LinkedList<JComponent> componentList = new LinkedList<>();
        componentList.offer(component);
        while (!componentList.isEmpty()) {
            JComponent child = componentList.poll();
            if (child != null) {
                handler.accept(child);
//        if (child instanceof IgnoreForeachChildComponent)
//          continue;
                for (Component com : child.getComponents()) {
                    if (com instanceof JComponent)
                        componentList.offer((JComponent) com);
                }
            }
        }
    }
}
