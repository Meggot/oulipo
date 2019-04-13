// Copyright (c) 2019 Travelex Ltd

package com.user.controllers;

import com.common.models.dtos.AccountDto;
import com.common.models.dtos.AccountGroupMembershipDto;
import com.common.models.dtos.AccountRelationshipDto;
import com.common.models.dtos.AccountTagDto;
import com.common.models.requests.*;
import com.querydsl.core.types.Predicate;
import com.user.controllers.assemblers.*;
import com.user.dao.entites.*;
import com.user.dao.repository.AccountRepository;
import com.user.services.AccountManagementService;
import com.user.services.AccountRelationshipManagementService;
import com.user.services.AccountTagManagementService;
import com.user.services.GroupManagementService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/accounts", produces = "application/json")
public class AccountController {

    @Autowired
    AccountManagementService accountManagementService;

    @Autowired
    AccountRelationshipManagementService accountRelationshipManagementService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountResourceAssembler accountResourceAssembler;

    @Autowired
    AccountRelationshipAssembler accountRelationshipAssembler;

    @Autowired
    AccountGroupMembershipAssembler accountGroupMembershipAssembler;

    @Autowired
    AccountTagManagementService accountTagManagementService;

    @Autowired
    AccountTagAssembler accountTagAssembler;

    @Autowired
    GroupManagementService groupManagementService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Resource<AccountDto> createAccountRequest(@Valid @ModelAttribute("CreateAccount") CreateAccount createAccount) {
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
    @RequestMapping(value = "/thisAccount", method = RequestMethod.GET)
    public Resource<AccountDto> getAccount(@RequestHeader("User") Account account, Model model) {
        model.addAttribute("account", account);
        return new Resource<>(accountResourceAssembler.toResource(account));
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

    @ResponseBody
    @RequestMapping(value = "/{accountId}/relationship", method = RequestMethod.POST)
    public Resource<AccountRelationshipDto> postRelationshipRequest(@ModelAttribute @Valid AccountRelationshipRequest request,
                                                                    @RequestHeader("User") Account accountReq,
                                                                    @PathVariable("accountId") Account account) {
        AccountRelationship relationship = accountRelationshipManagementService.postRelationshipRequest(request, accountReq, account);
        return new Resource<>(accountRelationshipAssembler.toResource(relationship));
    }

    @ResponseBody
    @RequestMapping(value = "/{accountId}/tags", method = RequestMethod.POST)
    public Resource<AccountTagDto> postAccountTag(@ModelAttribute @Valid AccountTagRequest request,
                                                  @PathVariable("accountId") Account accountToAdd,
                                                  @RequestHeader("User") Account account) {
        AccountTag accountTag = accountTagManagementService.handleAccountTagRequest(request, accountToAdd);
        return new Resource<>(accountTagAssembler.toResource(accountTag));
    }

    @ResponseBody
    @RequestMapping(value = "/{accountId}/group/{groupId}", method = RequestMethod.POST)
    public Resource<AccountGroupMembershipDto> postAccountMembership(@Valid  @ModelAttribute PostAccountGroupMembershipRequest request,
                                                                     @RequestHeader("User") Account addedBy,
                                                                     @PathVariable("accountId") Account added,
                                                                     @PathVariable("groupId") Group group) {
        AccountGroupMembership accountGroupMembership = groupManagementService.handlePostAccountGroupMembershipRequest(request, addedBy, added, group);
        return new Resource<>(accountGroupMembershipAssembler.toResource(accountGroupMembership));
    }
}
