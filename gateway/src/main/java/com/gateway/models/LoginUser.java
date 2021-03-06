// Copyright (c) 2019 Travelex Ltd

package com.gateway.models;

import lombok.Data;

import java.util.List;

@Data
public class LoginUser {

    boolean activated;

    List<Authority> authorities;

    String login;

    String password;

    Integer userId;

}
