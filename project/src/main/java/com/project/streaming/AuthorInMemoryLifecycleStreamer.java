package com.project.streaming;

import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import com.project.services.AuthorManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("Test")
public class AuthorInMemoryLifecycleStreamer implements AuthorLifecycleListener{

    @Autowired
    private AuthorManagementService authorManagementService;

    @Override
    public void handleUserCreationEvent(AccountCreationMessage accountCreationMessage) {
        authorManagementService.createAuthor(accountCreationMessage.getAccountId(), accountCreationMessage.getUsername());
    }

    @Override
    public void handleUserUpdateEvent(AccountUpdateMessage accountUpdateMessage) {
        authorManagementService.updateAuthor(accountUpdateMessage.getAccountId(), accountUpdateMessage.getNewUsername());
    }
}
