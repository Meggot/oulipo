// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@JsonInclude(NON_NULL)
@Relation("projects")
public class ProjectDto extends ResourceSupport implements Identifiable<Link> {

    private int projectId;

    private String title;

    private String synopsis;

    private String type;

    private String visibilityType;

    private String sourcingType;

    private String originalAuthor;

    private String creationDate;

    private String modifiedDate;

    private CopyDto copy;

    private List<ProjectPartDto> parts;

    private List<AuthorProjectRoleDto> roles;

    private List<ProjectTagDto> tags;

    private int version;

}
