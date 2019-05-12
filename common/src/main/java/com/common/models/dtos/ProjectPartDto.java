// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation("part")
public class ProjectPartDto extends ResourceSupport implements Identifiable<Link> {

    private Integer idField;
    private Integer projectId;
    private String projectTitle;
    private PartStatus status;
    private String activeValue;
    private Integer sequence;
    private String authorName;
    private Integer authorUserId;
    private LocalDateTime added;
}
