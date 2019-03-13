// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(NON_NULL)
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
