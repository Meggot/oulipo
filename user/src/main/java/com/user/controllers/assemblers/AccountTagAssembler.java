package com.user.controllers.assemblers;

import com.common.models.dtos.AccountTagDto;
import com.user.controllers.AccountTagController;
import com.user.dao.entites.AccountTag;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class AccountTagAssembler extends ResourceAssemblerSupport<AccountTag, AccountTagDto> {

    public AccountTagAssembler() {
        super(AccountTagController.class, AccountTagDto.class);
    }

    @Override
    public AccountTagDto toResource(AccountTag accountTag) {
        AccountTagDto dto = createResourceWithId(accountTag.getId(), accountTag);
        dto.setCategory(accountTag.getCategory());
        dto.setIdField(accountTag.getId());
        dto.setType(accountTag.getType());
        dto.setValue(accountTag.getValue());
        return dto;
    }
}
