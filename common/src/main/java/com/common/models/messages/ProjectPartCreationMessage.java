package com.common.models.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPartCreationMessage implements Serializable {

    private Integer partId;

    private Integer projectId;

    private String projectTitle;

    private String partAuthorName;

    private String partUserId;

}
