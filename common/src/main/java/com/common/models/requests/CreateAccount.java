// Copyright (c) 2019 Travelex Ltd

package com.common.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class CreateAccount {

    @NotNull
    @Size(min = 3, max = 10)
    String username;

    @NotNull
    @Email
    String email;

    @NotNull
    String hashedPassword;

}
