package com.common.models.dtos;

import com.common.models.enums.GroupRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation("accountMembership")
public class AccountGroupMembershipDto extends ResourceSupport implements Identifiable<Link> {

    private Integer idField;

    private String accountUsername;

    private Integer accountId;

    private String addedByUsername;

    private Integer addedById;

    private Integer groupId;

    private String groupName;

    private GroupRole role;

    private LocalDateTime added;
}
