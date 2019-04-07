// Copyright (c) 2019 Travelex Ltd

package com.gateway.security.client;

import com.gateway.models.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserClient {

    @Autowired
    private UserFeignClient userFeignClient;

    public Optional<LoginUser> findOneWithAuthoritiesByEmail(String login) {
        LoginUser loginUser = userFeignClient.loginWithUserName(login);
        return Optional.of(loginUser);
    }

    public Optional<LoginUser> findOneWithAuthoritiesByLogin(String lowercaseLogin) {
        return this.findOneWithAuthoritiesByEmail(lowercaseLogin);
    }

    public boolean logAttempt(String username, String inetAddress) {
        return userFeignClient.addLoginAttempt(username, inetAddress);
    }
}
