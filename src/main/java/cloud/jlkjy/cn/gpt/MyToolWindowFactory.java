package cloud.jlkjy.cn.gpt;

import cloud.jlkjy.cn.gpt.ui.GptCodeGenWindowPanel;
import cloud.jlkjy.cn.gpt.ui.Test;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class MyToolWindowFactory implements ToolWindowFactory, DumbAware {

    public MyToolWindowFactory() {

    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        ContentFactory factory = toolWindow.getContentManager().getFactory();
        GptCodeGenWindowPanel mapPanel = new GptCodeGenWindowPanel();
        Content content = factory.createContent(mapPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }


}
