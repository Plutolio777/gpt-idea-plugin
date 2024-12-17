package cloud.jlkjy.cn.gpt.ui.components.textpanel;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.ComponentUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.TextComponentEmptyText;
import com.intellij.util.ObjectUtils;
import com.intellij.util.ui.JBInsets;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.StatusText;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class RichTextPanel extends JTextPane {
    private static final Logger LOGGER = Logger.getInstance(RichTextPanel.class);
    public final TextComponentEmptyText myEmptyText;

    public RichTextPanel() {
        this.setEditorKit(new ChatStyledEditorKit());
        this.myEmptyText = new TextComponentEmptyText(this);
        this.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                System.out.println("asdasdasd");
                RichTextPanel.this.invalidate();
                RichTextPanel.this.revalidate();
                RichTextPanel.this.repaint();
            }
        });
    }

    public java.util.List<Element> getUIElements() {
        List<Element> elements = new ArrayList<>();
        StyledDocument doc = getStyledDocument();
        ElementIterator iterator = new ElementIterator(doc);
        Element element;
        while ((element = iterator.next()) != null) {
            try {
                if (element.getName().equals("content")) {
                    elements.add(element);

                }
//                if (element.getName().equals("component")) {
//                    Component comp = StyleConstants.getComponent(element.getAttributes());
//                    if (comp != null && comp instanceof ChatAskTagLabel)
//                        elements.add(element);
//                }
            } catch (Exception e) {
                LOGGER.warn("build input elements error " + e.getMessage(), e);
            }
        }
        return elements;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!this.myEmptyText.getStatusTriggerText().isEmpty() && this.myEmptyText.isStatusVisible()) {
            g.setColor(getBackground());
            Rectangle rect = new Rectangle(getSize());
            JBInsets.removeFrom(rect, getInsets());
            JBInsets.removeFrom(rect, getMargin());
            ((Graphics2D)g).fill(rect);
            g.setColor(getForeground());
        }
        this.myEmptyText.paintStatusText(g);
    }

    public void insertText(int offset, String text) {
        try {
            getStyledDocument().insertString(offset, text, null);
        } catch (BadLocationException e) {
            LOGGER.warn("insertString error " + e.getMessage(), e);
        }
    }

    public void insertText(String text) {
        insertText(getCaretPosition(), text);
    }

    public static class TextComponentEmptyText extends StatusText {
        private final JTextComponent myOwner;

        private String myStatusTriggerText = "";

        TextComponentEmptyText(JTextComponent owner) {
            super(owner);
            this.myOwner = owner;
            clear();
            this.myOwner.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {
                    RichTextPanel.TextComponentEmptyText.this.myOwner.repaint();
                }

                public void focusLost(FocusEvent e) {
                    RichTextPanel.TextComponentEmptyText.this.myOwner.repaint();
                }
            });
        }

        public void setTextToTriggerStatus(@NotNull String defaultText) {
            this.myStatusTriggerText = defaultText;
        }

        @NotNull
        public String getStatusTriggerText() {
            return this.myStatusTriggerText;
        }

        public void paintStatusText(Graphics g) {
            if (!isFontSet())
                setFont(this.myOwner.getFont());
            paint(this.myOwner, g);
        }

        protected boolean isStatusVisible() {
            return (this.myOwner.getText().equals(this.myStatusTriggerText) && !this.myOwner.isFocusOwner());
        }

        protected Rectangle getTextComponentBound() {
            Rectangle b = this.myOwner.getBounds();
            Insets insets = (Insets)ObjectUtils.notNull(this.myOwner.getInsets(), JBUI.emptyInsets());
            Insets margin = (Insets) ObjectUtils.notNull(this.myOwner.getMargin(), JBUI.emptyInsets());
            Insets ipad = getComponent().getIpad();
            int left = insets.left + margin.left - ipad.left;
            int right = insets.right + margin.right - ipad.right;
            int top = insets.top + margin.top - ipad.top;
            int bottom = insets.bottom + margin.bottom - ipad.bottom;
            return new Rectangle(left, top, b.width - left - right, b.height - top - bottom);
        }

        @NotNull
        protected Rectangle adjustComponentBounds(@NotNull JComponent component, @NotNull Rectangle bounds) {
            Dimension size = component.getPreferredSize();
            return (component == getComponent()) ? new Rectangle(bounds.x, bounds.y, size.width, bounds.height) : new Rectangle(bounds.x + bounds.width - size.width, bounds.y, size.width, bounds.height);
        }
    }

}
