// Copyright (c) 2019 Travelex Ltd

package com.gateway;

import com.common.models.dtos.AccountDto;
import com.common.models.requests.CreateAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gateway.security.jwt.JWTFilter;
import com.gateway.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping
@Slf4j
@RestController
public class UserAwtController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    public UserAwtController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @ResponseBody
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authorize(@Valid @ModelAttribute LoginVM loginVM) {
        log.info("> [AUTHENTICATE] Authenticating login request: {}", loginVM);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
        Authentication authentication;
        try {
            authentication = this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException badCreds) {
            return new ResponseEntity<>(new AuthenticationResponse(null, "Bad Credentials."), HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new AuthenticationResponse(jwt, "Authentication Successful."), httpHeaders, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/role")
    public ResponseEntity<String> areYouAdmin(HttpServletRequest servletRequest) {
        if (servletRequest.isUserInRole("ROLE_ADMIN")) {
            return new ResponseEntity<>("ADMIN YES", HttpStatus.OK);
        } else if (servletRequest.isUserInRole("ROLE_USER")) {
            return new ResponseEntity<>("USER YES", HttpStatus.OK);
        }
        return new ResponseEntity<>("YOU DONT AVE ANY ROLEE", HttpStatus.FORBIDDEN);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class AuthenticationResponse {

        private String idToken;

        private String message;

    }

    @Data
    @NoArgsConstructor
    public class LoginVM {

        String username;

        String password;

        boolean rememberMe;

    }
}
