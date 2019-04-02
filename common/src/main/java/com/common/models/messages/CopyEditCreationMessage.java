package com.common.models.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopyEditCreationMessage {
    private Integer copyId;

    private String delta;

    private String authorName;

    private String projectTitle;
}
