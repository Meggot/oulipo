// Copyright (c) 2019 Travelex Ltd

package com.user.controllers.assemblers;

import com.common.models.dtos.AccountDto;
import com.user.controllers.AccountController;
import com.user.dao.entites.Account;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class AccountResourceAssembler extends ResourceAssemblerSupport<Account, AccountDto> {

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
        return resource;
    }
}
