package com.user.controllers;

import com.common.models.dtos.AccountRelationshipDto;
import com.common.models.requests.UpdateAccountRelationshipRequest;
import com.querydsl.core.types.Predicate;
import com.user.controllers.assemblers.AccountRelationshipAssembler;
import com.user.dao.entites.Account;
import com.user.dao.entites.AccountRelationship;
import com.user.dao.repository.AccountRelationshipRepository;
import com.user.services.AccountRelationshipManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/relationship")
public class AccountRelationshipController {

    @Autowired
    private AccountRelationshipAssembler assembler;

    @Autowired
    private AccountRelationshipRepository relationshipRepository;

    @Autowired
    private AccountRelationshipManagementService accountRelationshipManagementService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<AccountRelationshipDto> findAllRelationships(@QuerydslPredicate(root=AccountRelationship.class)
                                                                                   Predicate predicate,
                                                                       Pageable pageable, @RequestHeader("User") Account account,
                                                                       PagedResourcesAssembler pagedResourcesAssembler) {
        log.info("ACCOUNT {} ", account.getUsername());
        Page<AccountRelationshipDto> page = relationshipRepository.findAll(predicate, pageable).map(entity -> assembler.toResource(entity));
        return pagedResourcesAssembler.toResource(page);
    }

    @ResponseBody
    @RequestMapping(path = "/{relationshipId}", method = RequestMethod.GET)
    public Resource<AccountRelationshipDto> getById(@PathVariable("relationshipId") AccountRelationship accountRelationship) {
        return new Resource<>(assembler.toResource(accountRelationship));
    }

    @ResponseBody
    @RequestMapping(path = "/{relationshipId}", method = RequestMethod.PATCH)
    public Resource<AccountRelationshipDto> patchOnId(@PathVariable("relationshipId") AccountRelationship accountRelationship,
                                                      @RequestHeader("User") Account account,
                                                      @ModelAttribute UpdateAccountRelationshipRequest request) {
        AccountRelationship relationshipEntity = accountRelationshipManagementService.patchRelationship(accountRelationship, account, request);
        return new Resource<>(assembler.toResource(relationshipEntity));
    }
}
