package com.project.streaming;

import com.common.models.dtos.AccountDto;
import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import com.common.models.messages.Message;

public interface AuthorLifecycleListener {

    void handleUserCreationEvent(Message<AccountDto> accountCreationMessage);

    void handleUserUpdateEvent(Message<AccountDto> accountUpdateMessage);
}
