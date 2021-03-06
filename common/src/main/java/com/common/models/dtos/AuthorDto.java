// Copyright (c) 2019 Travelex Ltd

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
@Relation("author")
public class AuthorDto extends ResourceSupport implements Identifiable<Link> {

    Integer idField;
    Integer userId;
    String username;
    List<ProjectDto> createdProjects;
    List<ProjectPartDto> createdParts;

}
