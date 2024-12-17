package cloud.jlkjy.cn.gpt.ui.components.textpanel;

import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import com.intellij.ui.JBColor;

import java.awt.*;
import com.intellij.openapi.diagnostic.Logger;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ChatTextPanel extends RichTextPanel {
    private static final Logger LOGGER = Logger.getInstance(ChatTextPanel.class);
    private String placeholder;
    private boolean isPlaceholderVisible = true;
    private int maxHeight;

    public ChatTextPanel() {
        this("输入问题或粘贴图片提问");
        setBackground(ColorUtil.getEditorBackgroundColor());
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    if (e.isControlDown() || e.isMetaDown()) {
                        e.consume();
                        int pos = ChatTextPanel.this.getCaretPosition();
                        ChatTextPanel.this.insertText(pos, "\n");
                    } else if (e.getModifiersEx() == 0) {
                        e.consume();
                    }
                    ChatTextPanel.this.revalidate();
                }
            }
        });
        this.myEmptyText.setText(this.placeholder);
    }

    public ChatTextPanel(String placeholder) {
        this.placeholder = placeholder;
    }

    public ChatTextPanel(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintSecondaryPlaceholder(g);
        // 如果没有焦点且文本为空，则显示占位符
    }

    private void paintSecondaryPlaceholder(Graphics g) {
//        if (getText().isEmpty() || isFocusOwner() || StringUtils.isBlank(this.placeholder))
//            return;
//        List<Element> elements = getUIElements();
//        if (elements.isEmpty() || elements.size() > 2)
//            return;
//        int validCount = elements.size();
//        Element lastElement = elements.get(elements.size() - 1);
//        StyledDocument doc = getStyledDocument();
//        try {
//            if (lastElement.getName().equals("content")) {
//                int start = lastElement.getStartOffset();
//                int end = lastElement.getEndOffset();
//                String text = doc.getText(start, end - start);
//                if ("\n".equals(text))
//                    validCount--;
//            }
//        } catch (Exception e) {
//            return;
//        }
//        if (validCount != 1)
//            return;
//        String childPlaceholder = null;
//        Element firstElement = elements.get(0);
//        Component targetComp = null;
//
//        Graphics2D g2 = (Graphics2D) g.create();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setColor(ColorUtil.getAuxiliaryForegroundColor());
//        FontMetrics fm = g2.getFontMetrics();
//        int x = (targetComp.getLocation()).x + targetComp.getWidth() + 8;
//        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
//        g2.drawString(childPlaceholder, x, y);
//        g2.dispose();
    }


}
