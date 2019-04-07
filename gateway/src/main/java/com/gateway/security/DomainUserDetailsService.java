package com.gateway.security;

import com.gateway.models.LoginUser;
import com.gateway.security.client.UserClient;
import com.netflix.zuul.context.RequestContext;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserClient userClient;

    public DomainUserDetailsService(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        if (new EmailValidator().isValid(login, null)) {
            return userClient.findOneWithAuthoritiesByEmail(login)
                             .map(user -> createSpringSecurityUser(login, user))
                             .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }

        //String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userClient.findOneWithAuthoritiesByLogin(login)
                         .map(user -> createSpringSecurityUser(login, user))
                         .orElseThrow(() -> new UsernameNotFoundException("User " + login + " was not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, LoginUser user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities =
                user.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getUserId()), user.getPassword(), grantedAuthorities);
    }
}
