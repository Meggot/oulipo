// Copyright (c) 2019 Travelex Ltd

package com.user.controllers;

import com.common.models.dtos.AccountDto;
import com.common.models.dtos.Authority;
import com.common.models.dtos.LoginUser;
import com.common.models.requests.CreateAccount;
import com.common.models.requests.LoginRequest;
import com.common.models.responses.AuthenticateResponse;
import com.common.models.responses.LoginResponse;
import com.user.controllers.assemblers.AccountResourceAssembler;
import com.user.dao.entites.Account;
import com.user.dao.repository.AccountRepository;
import com.user.services.AccountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/login", produces = "application/json")
public class LoginController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountResourceAssembler accountResourceAssembler;

    @Autowired
    AccountManagementService accountManagementService;

    @ResponseBody
    @PostMapping
    public LoginUser login(@RequestBody String username) {
        LoginUser loginUser = new LoginUser();
        accountRepository.getAccountByUsername(username).map(account -> {
            loginUser.setActivated(true);
            loginUser.setLogin(account.getUsername());
            loginUser.setPassword(account.getPassword().getHashValue());
            loginUser.setAuthorities(Arrays.asList(new Authority("ROLE_USER")));
            return loginUser;
        });
        return loginUser;
    }

    @ResponseBody
    @PostMapping("/register")
    public Resource<AccountDto> registerAccount(@RequestBody CreateAccount registerAccount) {
        Account account = accountManagementService.createAccount(registerAccount);
        return new Resource<>(accountResourceAssembler.toResource(account));
    }

    @ResponseBody
    @RequestMapping("/{sessionToken}")
    public AuthenticateResponse authenticateResponse(@PathVariable(name = "sessionToken") String sessionToken) {
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        authenticateResponse.setAuthenticated(true);
        return authenticateResponse;
    }

}

