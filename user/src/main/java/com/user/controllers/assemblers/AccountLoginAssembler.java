package com.user.controllers.assemblers;

import com.common.models.dtos.AccountLoginDto;
import com.user.controllers.AccountController;
import com.user.dao.entites.AccountLogin;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class AccountLoginAssembler  extends ResourceAssemblerSupport<AccountLogin, AccountLoginDto> {

    public AccountLoginAssembler() {
        super(AccountController.class, AccountLoginDto.class);
    }

    @Override
    public AccountLoginDto toResource(AccountLogin accountLogin) {
        AccountLoginDto accountLoginDto = createResourceWithId(accountLogin.getId(), accountLogin);
        accountLoginDto.setInetAddress(accountLogin.getIpAddress());
        accountLoginDto.setLoginTime(accountLogin.getCreationDate().toString());
        return accountLoginDto;
    }

}
