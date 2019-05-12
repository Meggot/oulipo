// Copyright (c) 2019 Travelex Ltd

package com.user.streaming;

import com.common.models.dtos.AccountDto;
import com.common.models.dtos.MessageDto;
import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import com.common.models.messages.Message;
import com.common.models.messages.MessageSentMessage;

public interface UserLifecycleStreamer {

    void sendAccountCreationMessage(Message<AccountDto> accountCreationMessage);

    void sendAccountUpdateMessage(Message<AccountDto> accountUpdateMessage);

    void sendMessageSentMessage(Message<MessageDto> messageSentMessage);

}