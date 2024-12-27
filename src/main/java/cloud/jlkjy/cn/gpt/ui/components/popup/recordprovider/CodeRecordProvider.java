package cloud.jlkjy.cn.gpt.ui.components.popup.recordprovider;

import cloud.jlkjy.cn.gpt.core.codestruct.CodeStructServiceManager;
import cloud.jlkjy.cn.gpt.location.CustomBundle;
import cloud.jlkjy.cn.gpt.model.PopupRecord;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;

import java.util.List;
import java.util.stream.Collectors;

public class CodeRecordProvider extends FileRecordProvider{


    @Override
    public List<PopupRecord> listSuggestPrompts(Project project, String query, PopupRecord parentPopupRecord) {
        CodeStructServiceManager service = ApplicationManager.getApplication().getService(CodeStructServiceManager.class);
        List<PopupRecord> popupRecords = super.listSuggestPrompts(project, query, parentPopupRecord);
        List<PopupRecord> filteredRecords = popupRecords.stream().filter(record -> service.canBeParseToStruct(record.getHintText())).toList();
        for (PopupRecord popupRecord : filteredRecords) {
            String path = popupRecord.getHintText();
            if (service.canBeParseToStruct(path)) {
                popupRecord.setCanDrillDown(true);
                popupRecord.setSelectHintText(CustomBundle.message("ui.chat.popup.text.select.code", new Object[0]));
                popupRecord.setDrillDownType("Struct");
            }
        }
        return filteredRecords;
    }
}
