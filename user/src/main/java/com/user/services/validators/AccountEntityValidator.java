// Copyright (c) 2019 Travelex Ltd

package com.user.services.validators;

import com.common.models.dtos.AccountDto;
import com.common.models.exceptions.ApiValidationError;
import com.common.models.requests.CreateAccount;
import com.common.models.requests.UpdateAccount;
import com.common.models.utils.ValidationUtil;
import com.user.dao.entites.Account;
import com.user.dao.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Slf4j
@Component
public class AccountEntityValidator {

    @Autowired
    AccountRepository accountRepository;

    public Set<ApiValidationError> validateAccountUpdateRequest(Account oldAccount, UpdateAccount newUpdateDto) {
        Set<ApiValidationError> apiSubErrors = new HashSet<>();
        if (newUpdateDto.getUsername() != null && !oldAccount.getUsername().equalsIgnoreCase(newUpdateDto.getUsername())) {
            if (accountRepository.existsAccountByUsername(newUpdateDto.getUsername())) {
                apiSubErrors.add(ApiValidationError.builder()
                                                   .field("Username")
                                                   .rejectedValue(newUpdateDto.getUsername())
                                                   .message("That username is already in use.")
                                                   .build());
            }
        }
        if (newUpdateDto.getEmail() != null && !oldAccount.getEmail().equalsIgnoreCase(newUpdateDto.getEmail())) {
            if (accountRepository.existsAccountByEmail(newUpdateDto.getEmail())) {
                apiSubErrors.add(ApiValidationError.builder()
                                                   .field("Email")
                                                   .rejectedValue(newUpdateDto.getEmail())
                                                   .message("That email is already in use.")
                                                   .build());
            }
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UpdateAccount>> violations = validator.validate(newUpdateDto);
        if (!violations.isEmpty()) {
            violations.forEach(c -> apiSubErrors.add(ValidationUtil.mapConstraintViolationToApiValidationError(c)));
        }
        return apiSubErrors;
    }

    public Set<ApiValidationError> validateCreateAccountRequest(CreateAccount createAccountRequest) {
        Set<ApiValidationError> apiSubErrors = new HashSet<>();
        if (accountRepository.existsAccountByUsername(createAccountRequest.getUsername())) {
            apiSubErrors.add(ApiValidationError.builder()
                                               .field("Username")
                                               .rejectedValue(createAccountRequest.getUsername())
                                               .message("That username is already in use.")
                                               .build());
        }
        if (accountRepository.existsAccountByEmail(createAccountRequest.getEmail())) {
            apiSubErrors.add(ApiValidationError.builder()
                                               .field("Email")
                                               .rejectedValue(createAccountRequest.getEmail())
                                               .message("That email is already in use.")
                                               .build());
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CreateAccount>> violations = validator.validate(createAccountRequest);
        if (!violations.isEmpty()) {
            violations.forEach(c -> apiSubErrors.add(ValidationUtil.mapConstraintViolationToApiValidationError(c)));
        }
        return apiSubErrors;
    }

}