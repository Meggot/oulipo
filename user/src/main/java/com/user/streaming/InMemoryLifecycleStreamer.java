// Copyright (c) 2019 Travelex Ltd

package com.user.streaming;

import com.common.models.dtos.AccountDto;
import com.common.models.dtos.MessageDto;
import com.common.models.messages.Message;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("Test")
@Primary
@Component
public class InMemoryLifecycleStreamer implements UserLifecycleStreamer{

    @Override
    public void sendAccountCreationMessage(Message<AccountDto> accountCreationMessage) {

    }

    @Override
    public void sendAccountUpdateMessage(Message<AccountDto> accountUpdateMessage) {

    }

    @Override
    public void sendMessageSentMessage(Message<MessageDto> messageSentMessage) {

    }
}
