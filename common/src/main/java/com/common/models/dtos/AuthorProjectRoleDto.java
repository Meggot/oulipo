package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation("role")
public class AuthorProjectRoleDto extends ResourceSupport implements Identifiable<Link> {

    public Integer idField;

    public Integer authorId;

    public String authorName;

    public Integer projectId;

    public String projectTitle;

    public AuthorProjectRoleType role;

}
