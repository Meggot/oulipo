package com.user.controllers.assemblers;

import com.common.models.dtos.AccountRelationshipDto;
import com.user.controllers.AccountRelationshipController;
import com.user.dao.entites.AccountRelationship;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class AccountRelationshipAssembler extends ResourceAssemblerSupport<AccountRelationship, AccountRelationshipDto> {


    public AccountRelationshipAssembler() {
        super(AccountRelationshipController.class, AccountRelationshipDto.class);
    }

    @Override
    public AccountRelationshipDto toResource(AccountRelationship accountRelationship) {
        AccountRelationshipDto accountRelationshipDto = createResourceWithId(accountRelationship.getId(), accountRelationship);
        accountRelationshipDto.setIdField(accountRelationship.getId());
        accountRelationshipDto.setAddedByUsername(accountRelationship.getAddedBy().getUsername());
        accountRelationshipDto.setAddedUsername(accountRelationship.getAdded().getUsername());
        accountRelationshipDto.setStatus(accountRelationship.getStatus());
        accountRelationshipDto.setType(accountRelationship.getRelationshipType());
        return accountRelationshipDto;
    }
}
