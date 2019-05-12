// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import javax.validation.Valid;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
@Relation("account")
public class AccountDto extends ResourceSupport implements Identifiable<Link> {

    private int idField;

    private String username;

    private String hashedPassword;

    private String email;

    private AccountStatus status;

    private List<AccountLoginDto> logins;

    private List<AccountRelationshipDto> relationships;

    private List<AccountGroupMembershipDto> groups;

    private List<AccountTagDto> tags;

    private List<MessageDto> sentMessages;

    private List<MessageDto> receivedMessages;

}
