package com.common.models.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPartUpdateMessage {

    private Integer partId;

    private Integer projectId;

    private String projectTitle;

    private String partAuthorName;

    private String partUserId;

    private String partValue;

    private String partStatus;
}
