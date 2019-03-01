// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
public class LoginUser {

    boolean activated;

    List<Authority> authorities;

    String login;

    String password;

    Integer userId;

}
