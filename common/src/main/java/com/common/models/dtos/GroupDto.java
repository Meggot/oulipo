package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation("group")
public class GroupDto extends ResourceSupport implements Identifiable<Link> {
    private Integer idField;

    private String name;

    private String description;

    private GroupType type;

    private List<AccountGroupMembershipDto> members;

    private List<ProjectGroupMembershipDto> projects;
}
