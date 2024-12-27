package cloud.jlkjy.cn.gpt.ui.components.popup.recordprovider;

import cloud.jlkjy.cn.gpt.ui.components.popup.recordprovider.codeprovider.CodeStructProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@AllArgsConstructor
@Getter
public enum PopupListSelector {
    Default("", null, new FunctionRecordProvider()),
    Function("function", null, new FunctionRecordProvider()),
    FILE("Files", null, new FileRecordProvider()),
    Code("Code", null, new CodeRecordProvider()),
    CodeStruct("Struct", null,new CodeStructProvider())
//    IMAGE("image", null, (RecordProvider)new ImageRecordProvider()),
//    FOLDER("folder", null, null),
//    SYMBOL("symbol", null, null),
//    CURRENT_FILE("currentFile", null, (RecordProvider)new FileChatContextRefProvider()),
//    SELECTED_CODE("selectedCode", null, (RecordProvider)new FileChatContextRefProvider()),
//    GIT_COMMIT("gitCommit", null, (RecordProvider)new GitCommitContextRefProvider()),
//    CODE_CHANGES("codeChanges", null, (RecordProvider)new CodeChangesContextRefProvider()),
//    CODEBASE("codebase", null, null),
//    TEAM_DOCS("teamDocs", null, null)
    ;

    private final String type;


    private final Icon defaultIcon;

    @NotNull
    private final RecordProvider provider;

    @NotNull
    public static PopupListSelector select(String type) {
        for (PopupListSelector value : values()) {
            if (value.type.equals(type))
                return value;
        }
        return Default;
    }
}
