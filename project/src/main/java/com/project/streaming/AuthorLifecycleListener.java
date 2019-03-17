package com.project.streaming;

import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;

public interface AuthorLifecycleListener {

    void handleUserCreationEvent(AccountCreationMessage accountCreationMessage);

    void handleUserUpdateEvent(AccountUpdateMessage accountUpdateMessage);
}
