package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRelationshipDto extends ResourceSupport implements Identifiable<Link> {

    private Integer idField;

    private String addedByUsername;

    private String addedUsername;

    private AccountRelationshipType type;

    private AccountRelationshipStatus status;

}
