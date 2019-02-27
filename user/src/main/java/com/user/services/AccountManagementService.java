package com.user.services;// Copyright (c) 2019 Travelex Ltd

import com.common.models.exceptions.ApiValidationError;
import com.common.models.exceptions.EntityValidationException;
import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import com.common.models.requests.CreateAccount;
import com.common.models.requests.UpdateAccount;
import com.user.dao.entites.Account;
import com.user.dao.entites.Password;
import com.user.dao.repository.AccountRepository;
import com.user.dao.repository.PasswordRepository;
import com.user.services.validators.AccountEntityValidator;
import com.user.streaming.JmsUserLifecycleStreamer;
import com.user.streaming.UserLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class AccountManagementService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountEntityValidator accountEntityValidator;

    @Autowired
    UserLifecycleStreamer userLifecycleStreamer;

    @Autowired
    PasswordRepository passwordRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account updateAccount(Integer accountId, UpdateAccount updateAccountRequest) {
        log.info(">[UPDATE] Starting updating Account {}", accountId);
        Account previousAccount = accountRepository.findById(accountId).get();
        Set<ApiValidationError> apiSubErrors = accountEntityValidator.validateAccountUpdateRequest(previousAccount, updateAccountRequest);
        if (!apiSubErrors.isEmpty()) {
            log.error(">[UPDATE] Account update FAILED, request: {}. SubErrors: {}", updateAccountRequest, apiSubErrors);
            throw new EntityValidationException(apiSubErrors, "Account update failed");
        }
        AccountUpdateMessage accountUpdateMessage = new AccountUpdateMessage();
        if (updateAccountRequest.getUsername() != null) {
            accountUpdateMessage.setOldUsername(previousAccount.getUsername());
            accountUpdateMessage.setNewUsername(updateAccountRequest.getUsername());
            previousAccount.setUsername(updateAccountRequest.getUsername());
        }
        if (updateAccountRequest.getEmail() != null) {
            accountUpdateMessage.setOldEmail(previousAccount.getEmail());
            accountUpdateMessage.setNewEmail(updateAccountRequest.getEmail());
            previousAccount.setEmail(updateAccountRequest.getEmail());
        }
        Account newAccount = accountRepository.save(previousAccount);
        accountUpdateMessage.setAccountId(String.valueOf(newAccount.getId()));
        log.info(">[UPDATE] Account {} updated SUCCESSFULLY", newAccount.getId());
        userLifecycleStreamer.sendAccountUpdateMessage(accountUpdateMessage);
        return newAccount;
    }

    public Account createAccount(CreateAccount createAccountRequest) {
        log.info(">[CREATION] Account creation received, request: {}", createAccountRequest);
        Set<ApiValidationError> apiSubErrors = accountEntityValidator.validateCreateAccountRequest(createAccountRequest);
        if (!apiSubErrors.isEmpty()) {
            log.error(">[CREATION] Account creation FAILED, request: {}. SubErrors: {}", createAccountRequest, apiSubErrors);
            throw new EntityValidationException(apiSubErrors, "Account creation failed.");
        }
        Account newAccount = new Account();
        newAccount.setUsername(createAccountRequest.getUsername());
        newAccount.setEmail(createAccountRequest.getEmail());

        Password newPassword = new Password();
        newPassword.setHashValue(passwordEncoder.encode(createAccountRequest.getHashedPassword()));
        newPassword = passwordRepository.save(newPassword);

        newAccount.setPassword(newPassword);
        newAccount = accountRepository.save(newAccount);
        AccountCreationMessage accountCreationMessage = new AccountCreationMessage();
        accountCreationMessage.setUsername(createAccountRequest.getUsername());
        accountCreationMessage.setEmail(createAccountRequest.getEmail());
        accountCreationMessage.setAccountId(String.valueOf(newAccount.getId()));
        log.info(">[CREATION] Account {} created successfully", newAccount);
        userLifecycleStreamer.sendAccountCreationMessage(accountCreationMessage);
        return newAccount;
    }

}
