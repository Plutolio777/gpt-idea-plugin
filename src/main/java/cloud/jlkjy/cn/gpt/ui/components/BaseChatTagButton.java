package cloud.jlkjy.cn.gpt.ui.components;

import cloud.jlkjy.cn.gpt.utils.SwingUtil;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class BaseChatTagButton extends RichRoundRectPanel{

    public BaseChatTagButton(LayoutManager layout) {
        super(layout);
    }

    public void setupKeyListener(Project project) {
        SwingUtil.foreachChildComponent((JComponent) this, com -> setupComponentKeyListener(com, project));
    }

    public void setupComponentKeyListener(final Component component, final Project project) {
        if (project == null)
            return;
        component.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (!component.isEnabled())
                    return;
                // 回车
                if (e.getKeyCode() == 10) {
                    BaseChatTagButton.this.handleClick();
                    // Backspace
                } else if (e.getKeyCode() == 8) {
                    BaseChatTagButton.this.handleDelete();
                }
                if (!e.isMetaDown() && !e.isControlDown())
                    return;

            }
        });
    }

    public void handleDelete() {
    }

    public abstract void handleClick();
}
