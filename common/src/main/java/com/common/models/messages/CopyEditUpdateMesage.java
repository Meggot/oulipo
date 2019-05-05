package com.common.models.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyEditUpdateMesage {

    private Integer copyId;

    private Integer projectId;

    private String delta;

    private String authorName;

    private String projectTitle;

    private String editStatus;
}
