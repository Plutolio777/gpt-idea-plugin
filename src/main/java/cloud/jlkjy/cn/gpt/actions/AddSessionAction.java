package cloud.jlkjy.cn.gpt.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;


public class AddSessionAction extends AnAction {

    public AddSessionAction() {
        super("新建会话", "新建一个会话", AllIcons.General.Add);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }
}
