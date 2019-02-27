// Copyright (c) 2019 Travelex Ltd

package com.user.streaming;

import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;

public interface UserLifecycleStreamer {

    void sendAccountCreationMessage(AccountCreationMessage accountCreationMessage);

    void sendAccountUpdateMessage(AccountUpdateMessage accountUpdateMessage);
}