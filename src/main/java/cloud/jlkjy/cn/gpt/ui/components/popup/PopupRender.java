package cloud.jlkjy.cn.gpt.ui.components.popup;

import org.jdesktop.swingx.renderer.DefaultListRenderer;

import javax.swing.*;
import java.awt.*;

public class PopupRender implements ListCellRenderer<PopupRecord> {

    private final DefaultListRenderer defaultRender = new DefaultListRenderer();

    public PopupRender() {

    }


    @Override
    public Component getListCellRendererComponent(JList<? extends PopupRecord> list, PopupRecord value, int index, boolean isSelected, boolean cellHasFocus) {
        return defaultRender.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
