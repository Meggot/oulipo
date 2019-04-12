// Copyright (c) 2019 Travelex Ltd

package com.user.streaming;

import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import com.common.models.messages.MessageSentMessage;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("Test")
@Primary
@Component
public class InMemoryLifecycleStreamer implements UserLifecycleStreamer{

    @Override
    public void sendAccountCreationMessage(AccountCreationMessage accountCreationMessage) {
        //DoNothing
    }

    @Override
    public void sendAccountUpdateMessage(AccountUpdateMessage accountUpdateMessage) {
        //DoNothing
    }

    @Override
    public void sendMessageSentMessage(MessageSentMessage messageSentMessage) {
        //DoNothing
    }
}
