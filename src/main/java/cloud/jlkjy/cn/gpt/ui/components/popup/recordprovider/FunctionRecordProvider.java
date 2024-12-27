package cloud.jlkjy.cn.gpt.ui.components.popup.recordprovider;

import cloud.jlkjy.cn.gpt.model.PopupRecord;
import cloud.jlkjy.cn.gpt.type.Icons;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionRecordProvider implements RecordProvider{
    @Override
    public List<PopupRecord> listSuggestPrompts(Project paramProject, String paramString, PopupRecord parentPopupRecord) {
        List<PopupRecord> allSuggestPrompt = new ArrayList<>();
        allSuggestPrompt.add(
            PopupRecord.functionBuilder().text("Files").selectHintText("文件搜索").canDrillDown(true).drillDownType("Files").icon(AllIcons.Nodes.Folder).build()
        );
        allSuggestPrompt.add(
            PopupRecord.functionBuilder().text("CodeBase").selectHintText("引用代码库").icon(Icons.CodeBase).build()
        );
        allSuggestPrompt.add(
            PopupRecord.functionBuilder().text("Code").selectHintText("代码搜索").canDrillDown(true).drillDownType("Code").icon(Icons.Code).build()
        );
        allSuggestPrompt.add(
            PopupRecord.functionBuilder().text("Docs").selectHintText("文档站点搜索").canDrillDown(true).drillDownType("Docs").icon(Icons.Docs).build()
        );
        allSuggestPrompt.add(
            PopupRecord.functionBuilder().text("Git Diff").canDrillDown(true).drillDownType("Git Diff").icon(Icons.GitDiff).build()
        );
        allSuggestPrompt.add(
            PopupRecord.functionBuilder().text("Terminal").selectHintText("引用终端内容").icon(Icons.Terminal).build()
        );
        allSuggestPrompt.add(
            PopupRecord.functionBuilder().text("Problems").selectHintText("当前文件中的问题").icon(Icons.Problem).build()
        );
        allSuggestPrompt.add(
            PopupRecord.functionBuilder().text("Folder").selectHintText("文件夹搜索").canDrillDown(true).drillDownType("Folder").icon(AllIcons.Nodes.Folder).build()
        );
        return allSuggestPrompt;
    }

    @Override
    public Icon getIconOfSuggestPrompt(Project paramProject, PopupRecord record) {
        return null;
    }
}
