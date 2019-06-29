// Copyright (c) 2019 Travelex Ltd

package com.user.streaming;

import com.common.models.dtos.AccountDto;
import com.common.models.dtos.MessageDto;
import com.common.models.messages.Message;

public interface UserLifecycleStreamer {

    void sendAccountCreationMessage(Message<AccountDto> accountCreationMessage);

    void sendAccountUpdateMessage(Message<AccountDto> accountUpdateMessage);

    void sendMessageSentMessage(Message<MessageDto> messageSentMessage);

}