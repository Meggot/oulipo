package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation("projectMembership")
public class ProjectGroupMembershipDto extends ResourceSupport implements Identifiable<Link> {

    private Integer idField;

    private Integer projectId;

    private String projectName;

    private String groupName;

    private Integer groupId;

    private String addedByUsername;

    private Integer addedById;

    private LocalDateTime added;
}
