package com.common.models.messages;

import com.common.models.dtos.TagType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTagUpdateMessage implements Serializable {

    private Integer tagId;

    private String projectTitle;

    private Integer projectId;

    private TagType type;

    private Integer userIdAdded;

    private String value;
}
