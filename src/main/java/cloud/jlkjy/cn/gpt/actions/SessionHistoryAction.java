package cloud.jlkjy.cn.gpt.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class SessionHistoryAction extends AnAction {
    public SessionHistoryAction() {
        super("会话历史", "Session history", AllIcons.General.InlineRefresh);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }
}
