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
import com.user.dao.entites.AccountLogin;
import com.user.dao.repository.AccountLoginRepository;
import com.user.dao.repository.AccountRepository;
import com.user.services.AccountManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping(produces = "application/json")
@Slf4j
public class LoginController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountResourceAssembler accountResourceAssembler;

    @Autowired
    private AccountManagementService accountManagementService;

    @Autowired
    private AccountLoginRepository accountLoginRepository;

    @ResponseBody
    @PostMapping("/login/{username}/addLogin")
    public boolean addLoginRequest(@PathVariable("username") String username,
                                   @RequestBody String ipAddress) {
        log.info("LOGIN REQUEST LOGGED: {} on {}", username, ipAddress);
        Account account =
                accountRepository.getAccountByUsername(username).orElseThrow(() -> new RuntimeException("Login attempt succeeded for an account that doesn't exist"));
        AccountLogin accountLogin = new AccountLogin();
        accountLogin.setIpAddress(ipAddress);
        account.addAccountLogin(accountLogin);
        accountLoginRepository.save(accountLogin);
        return true;
    }

    @ResponseBody
    @PostMapping("/login")
    public LoginUser login(@RequestBody String username) {
        LoginUser loginUser = new LoginUser();
        accountRepository.getAccountByUsername(username).map(account -> {
            loginUser.setActivated(true);
            loginUser.setUserId(account.getId());
            loginUser.setLogin(account.getUsername());
            loginUser.setPassword(account.getPassword().getHashValue());
            if (username.equals("ADMINA")) {
                loginUser.setAuthorities(Arrays.asList(new Authority("ROLE_ADMIN"), new Authority("ROLE_USER")));
            } else {
                loginUser.setAuthorities(Arrays.asList(new Authority("ROLE_USER")));
            }
            return loginUser;
        });
        return loginUser;
    }

    @ResponseBody
    @PostMapping("/api/register")
    public Resource<AccountDto> registerAccount(@Valid @RequestBody CreateAccount registerAccount) {
        Account account = accountManagementService.createAccount(registerAccount);
        return new Resource<>(accountResourceAssembler.toResource(account));
    }

}

