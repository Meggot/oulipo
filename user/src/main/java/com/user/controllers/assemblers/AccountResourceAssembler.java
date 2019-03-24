// Copyright (c) 2019 Travelex Ltd

package com.user.controllers.assemblers;

import com.common.models.dtos.AccountDto;
import com.user.controllers.AccountController;
import com.user.dao.entites.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AccountResourceAssembler extends ResourceAssemblerSupport<Account, AccountDto> {

    @Autowired
    private AccountLoginAssembler accountLoginAssembler;

    public AccountResourceAssembler() {
        super(AccountController.class, AccountDto.class);
    }

    @Override
    public AccountDto toResource(Account o) {
        AccountDto resource = createResourceWithId(o.getId(), o);
        resource.setIdField(o.getId());
        resource.setStatus(o.getStatus());
        resource.setUsername(o.getUsername());
        resource.setEmail(o.getEmail());
        resource.setLogins(o.getLogins().stream()
                .map(accountLoginAssembler::toResource)
                .collect(Collectors.toList()));
        return resource;
    }
}
