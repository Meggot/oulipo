// Copyright (c) 2019 Travelex Ltd

package com.user.controllers;

import com.common.models.dtos.AccountDto;
import com.common.models.exceptions.EntityValidationException;
import com.common.models.requests.CreateAccount;
import com.common.models.requests.UpdateAccount;
import com.common.models.responses.EntityModificationResponse;
import com.querydsl.core.types.Predicate;
import com.user.controllers.assemblers.AccountResourceAssembler;
import com.user.dao.entites.Account;
import com.user.dao.repository.AccountRepository;
import com.user.services.AccountManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;

@Slf4j
@RestController
@RequestMapping(path = "/accounts", produces = "application/json")
public class AccountController {

    @Autowired
    AccountManagementService accountManagementService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountResourceAssembler accountResourceAssembler;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Resource<AccountDto> createAccountRequest(@Valid @ModelAttribute("CreateAccount") CreateAccount createAccount, BindingResult bindingResult) {
        Account account = accountManagementService.createAccount(createAccount);
        return new Resource<>(accountResourceAssembler.toResource(account));
    }

    @ResponseBody
    @RequestMapping(value = "/{accountId}", method = RequestMethod.PATCH)
    public Resource<AccountDto> updateAccount(@PathVariable Integer accountId, @ModelAttribute("UpdateAccount") UpdateAccount updateAccount) {
        Account account =  accountManagementService.updateAccount(accountId, updateAccount);
        return new Resource<>(accountResourceAssembler.toResource(account));
    }

    @ResponseBody
    @RequestMapping("/{accountId}")
    public HttpEntity<Identifiable> findById(@PathVariable("accountId") Account account, Model model) {
        model.addAttribute("account", account);
        return new ResponseEntity<>(accountResourceAssembler.toResource(account), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<PagedResources<AccountDto>> findAll(Model model,
                                                          @QuerydslPredicate(root = Account.class) Predicate predicate,
                                                          Pageable pageable,
                                                          PagedResourcesAssembler assembler) {
        Page<AccountDto> accounts = accountRepository.findAll(predicate, pageable).map(account -> accountResourceAssembler.toResource(account));
        model.addAttribute("users", accounts);
        return new ResponseEntity<>(assembler.toResource(accounts), HttpStatus.OK);
    }
}
