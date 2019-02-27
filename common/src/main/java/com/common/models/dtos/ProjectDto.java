// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ProjectDto extends ResourceSupport implements Identifiable<Link> {

    private Integer idField;

    private String title;

    private String synopsis;

}
