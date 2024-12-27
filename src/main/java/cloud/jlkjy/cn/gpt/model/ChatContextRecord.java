package cloud.jlkjy.cn.gpt.model;


import lombok.Builder;
import lombok.Data;

import javax.swing.*;

@Data
@Builder
public class ChatContextRecord {

    private String contextId;
    private Icon icon;
    private String text;

    private Integer startLine;
    private Integer endLine;

}
