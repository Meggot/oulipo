// Copyright (c) 2019 Travelex Ltd

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
@Relation("projectMetadata")
public class ProjectMetaDataDto extends ResourceSupport implements Identifiable<Link> {

    private int idField;

    private int numUpvotes;

    private int numDownvotes;

    private int numFavourites;

    private int numShares;

}
