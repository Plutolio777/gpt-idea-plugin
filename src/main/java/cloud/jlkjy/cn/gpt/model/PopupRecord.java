package cloud.jlkjy.cn.gpt.model;

import com.intellij.icons.AllIcons;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.swing.*;
import java.io.Serializable;

@Data
@Builder
public class PopupRecord implements Serializable {

    private String text;

    private String type;

    private String linkText;

    private String selectHintText;

    private Icon selectHintIcon;

    private String hintText;

    private Icon HintIcon;

    private boolean enabled;

    private String id;

    private Icon icon;

    private boolean canDrillDown;

    private String drillDownType;

    private int level;

    public static PopupRecordBuilder functionBuilder() {
        return PopupRecord.builder().type("function").enabled(true);
    }

    public static PopupRecordBuilder returnBuilder(String parent) {
        return PopupRecord.builder().type("RETURN_FUNCTION").icon(AllIcons.Actions.Back).text(parent).selectHintText("点击后返回").enabled(true);
    }


}
