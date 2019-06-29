package com.user.dao.handlers;

import com.common.models.dtos.AccountDto;
import com.common.models.messages.Message;
import com.common.models.messages.MessageType;
import com.user.controllers.assemblers.AccountResourceAssembler;
import com.user.dao.entites.Account;
import com.user.streaming.UserLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
@Slf4j
public class AccountEventHandler {

    public static UserLifecycleStreamer userLifecycleStreamer;

    public static AccountResourceAssembler assembler;

    @Autowired
    public void setUserLifecycleStreamer(UserLifecycleStreamer userLifecycleStreamer) {
        AccountEventHandler.userLifecycleStreamer = userLifecycleStreamer;
    }

    @Autowired
    public void setAccountResourceAssembler(AccountResourceAssembler accountResourceAssembler){
        assembler = accountResourceAssembler;
    }

    @PostPersist
    public void onPostPersist(Account account) {
        AccountDto dto = assembler.toResource(account);
        Message<AccountDto> accountCreationMessage = new Message<>(dto, dto.getIdField(), MessageType.ACCOUNT_CREATION);
        userLifecycleStreamer.sendAccountCreationMessage(accountCreationMessage);
    }

    @PostUpdate
    public void onPostUpdate(Account account) {
        AccountDto dto = assembler.toResource(account);
        Message<AccountDto> accountUpdateMessage = new Message<>(dto, dto.getIdField(), MessageType.ACCOUNT_UPDATE);
        userLifecycleStreamer.sendAccountUpdateMessage(accountUpdateMessage);
    }
}
