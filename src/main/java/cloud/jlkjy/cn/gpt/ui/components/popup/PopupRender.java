package cloud.jlkjy.cn.gpt.ui.components.popup;

import cloud.jlkjy.cn.gpt.model.PopupRecord;
import cloud.jlkjy.cn.gpt.type.Fonts;
import cloud.jlkjy.cn.gpt.utils.ColorUtil;
import cloud.jlkjy.cn.gpt.utils.SwingUtil;
import com.intellij.icons.AllIcons;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.components.panels.HorizontalLayout;
import com.intellij.ui.components.panels.OpaquePanel;
import com.intellij.ui.scale.JBUIScale;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.text.Matcher;
import com.intellij.util.text.MatcherHolder;
import com.intellij.util.ui.JBInsets;
import com.intellij.util.ui.UIUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.renderer.DefaultListRenderer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

@Getter
@Setter
public class PopupRender extends DefaultListCellRenderer {

    private final DefaultListRenderer defaultRender = new DefaultListRenderer();
    private final PopupPanel popupPanel;
    private  JComponent hostComponent;

    public PopupRender(PopupPanel popupPanel, JComponent hostComponent) {
        this.hostComponent = hostComponent;
        this.popupPanel = popupPanel;
    }



    @Override
    @SuppressWarnings("unchecked")
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return this.getListCellRendererComponent((JList<PopupRecord>)list, (PopupRecord) value, index, isSelected, cellHasFocus);
    }

    public Component getListCellRendererComponent(JList<? extends PopupRecord> list, PopupRecord record, int index, boolean isSelected, boolean cellHasFocus) {
        if (record == null)
            return this.defaultRender.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);
        OpaquePanel opaquePanel = new OpaquePanel(new BorderLayout());
        opaquePanel.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));
        Component component = null;

        component = (new CustomColoredRender()).getListCellRendererComponent(list, record, index, isSelected, cellHasFocus);
        Color bgColor = component.getBackground();
        opaquePanel.add(component, "West");
        JPanel hintPanel = new JPanel((LayoutManager) new HorizontalLayout(5));
        hintPanel.setOpaque(false);
        opaquePanel.add(hintPanel, "East");
        opaquePanel.setBackground(isSelected ? ColorUtil.getItemSelectionBackground() : bgColor);
        hintPanel.add(new JLabel(""));
        if (StringUtils.isEmpty(record.getLinkText()) && (BooleanUtils.isTrue(isSelected) || BooleanUtils.isTrue(cellHasFocus))) {
            addSelectHint(component, record, hintPanel);
        } else {
            addHintText(component, record, hintPanel);
        }

        return opaquePanel;
    }

    private void addSelectHint(Component component, PopupRecord record, JPanel hintPanel) {
        String hintText = record.getSelectHintText();

        JLabel hintLabel = new JLabel("");
        if (hintText != null) {
            hintLabel.setText(hintText);
        }

        if (record.isCanDrillDown()) {
            hintLabel.setIcon(AllIcons.General.ArrowRight);
        }
        hintLabel.setFont(component.getFont().deriveFont(12.0F));
        hintLabel.setForeground(ColorUtil.getAuxiliaryForegroundColor());
        hintPanel.add(hintLabel);
    }

    private void addHintText(Component component, PopupRecord record, JPanel hintPanel) {
        if (BooleanUtils.isTrue(record.isCanDrillDown())) {
            JLabel drillDownLabel = new JLabel(AllIcons.General.ArrowRight);
            hintPanel.add(drillDownLabel);
            return;
        }

        if (StringUtils.isNotBlank(record.getHintText())) {
            String hint = record.getHintText();
            float maxWidth = (this.hostComponent.getWidth() - (component.getPreferredSize()).width - 8);
            hint = Fonts.getStringAdaptiveWidth(this, hint, (int) maxWidth, true);
            JLabel hintLabel = new JLabel(hint);
            hintLabel.setForeground(ColorUtil.getAuxiliaryForegroundColor());
            hintLabel.setFont(component.getFont().deriveFont(12.0F));
            JLabel learnMoreLabel = null;
            if (StringUtils.isNotBlank(record.getLinkText())) {
                learnMoreLabel = new JLabel("了解更多");
                hintLabel.setFont(component.getFont().deriveFont(12.0F));
                learnMoreLabel.setForeground(ColorUtil.getLinkForegroundColor());
                learnMoreLabel.setFont(component.getFont());
            }
            hintPanel.add(hintLabel);
            if (learnMoreLabel != null)
                hintPanel.add(learnMoreLabel);
        }

    }

    public class CustomColoredRender extends ColoredListCellRenderer<PopupRecord> {

        public CustomColoredRender() {

        }

        @Override
        protected void customizeCellRenderer(@NotNull JList<? extends PopupRecord> list, PopupRecord record, int index, boolean isSelected, boolean hasFocus) {
            Color color2 = ColorUtil.getListBackground();
            Color color1 = ColorUtil.getListForeground();
            Matcher matcher = MatcherHolder.getAssociatedMatcher(list);
            setPaintFocusBorder((hasFocus && UIUtil.isToUseDottedCellBorder()));
            if (record != null) {
                String text = record.getText();
                Color foregroundColor = record.isEnabled() ? color1 : ColorUtil.getInactiveForegroundColor();
                SimpleTextAttributes simpleTextAttributes = new SimpleTextAttributes(SimpleTextAttributes.STYLE_PLAIN, foregroundColor);
                String niceName = truncateText(text, isSelected);
                float maxWidth = PopupRender.this.hostComponent.getWidth() * 0.7F;
                niceName = Fonts.getStringAdaptiveWidth(this, niceName, (int) maxWidth, false);
                SpeedSearchUtil.appendColoredFragmentForMatcher(niceName, this, simpleTextAttributes, matcher, color2, isSelected);
                setCellPadding(2 + (record.getLevel()* 20));
                if (!Objects.equals(record.getId(), "CODE STRUCT")) {
                    setIconTextGap(JBUIScale.scale(4));
                }
                setCellIcon(record);
                if ("EMPTY_ITEM".equals(record.getId())) {
                    setBackground(color2);
                } else {
                    setBackground(isSelected ? ColorUtil.getItemSelectionBackground() : color2);
                }
            }
        }

        private String truncateText(String text, boolean isSelected) {
            if (isSelected) {
                int searchBarWidth = Math.max((PopupRender.this.hostComponent.getSize()).width, (PopupRender.this.hostComponent.getPreferredSize()).width);
                int popupWidth = (PopupRender.this.popupPanel.getPopupList() == null) ? 0 : (PopupRender.this.popupPanel.getPopupList().getPreferredSize()).width;
                int reservedWidth = (int) SwingUtil.getStringFontSize("...", getFont()).getWidth();
                int remainWidth = Math.max(popupWidth, searchBarWidth) - reservedWidth - 60;
                remainWidth = Math.max(30, remainWidth);
                StringBuilder sb = new StringBuilder();
                int bufferWidth = 0;
                for (char ch : text.toCharArray()) {
                    int charWidth = (int) SwingUtil.getStringFontSize(String.valueOf(ch), getFont()).getWidth();
                    bufferWidth += charWidth;
                    if (bufferWidth < remainWidth)
                        sb.append(ch);
                }
                if (!sb.toString().equals(text))
                    sb.append("...");
                return sb.toString();
            }
            return text;
        }


        private void setCellPadding(int scale) {
            setIpad(new JBInsets(1, scale, 1, 2));
        }


        private void setCellIcon(PopupRecord record) {
            if (record.getIcon() != null) {
                setIcon(record.getIcon());
            }
        }
    }
}
