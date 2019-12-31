package com.common.models.messages;

import com.common.models.enums.AuthorProjectRoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRoleUpdateMessage implements Serializable {

    public Integer id;

    public Integer authorUserId;

    public String authorName;

    public Integer projectId;

    public String projectTitle;

    public AuthorProjectRoleType role;

}
