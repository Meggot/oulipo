package com.common.models.dtos;

import com.common.models.enums.AccountRelationshipStatus;
import com.common.models.enums.AccountRelationshipType;
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
@Relation("accountRelationship")
public class AccountRelationshipDto extends ResourceSupport implements Identifiable<Link> {

    private Integer idField;

    private String addedByUsername;

    private Integer addedByUserId;

    private String addedUsername;

    private Integer addedUserId;

    private AccountRelationshipType type;

    private AccountRelationshipStatus status;

}
