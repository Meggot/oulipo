// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class AccountDto extends ResourceSupport implements Identifiable<Link> {

    private int idField;

    private String username;

    private String hashedPassword;

    private String email;

    private AccountStatus status;

}
