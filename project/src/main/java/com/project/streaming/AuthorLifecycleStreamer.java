// Copyright (c) 2019 Travelex Ltd

package com.project.streaming;

import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import com.project.services.AuthorManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorLifecycleStreamer {

    @Autowired
    AuthorManagementService authorManagementService;

    @KafkaListener(topics = "${jms.topic.user-lifecycle.creation}", groupId = "project",
            containerFactory = "accountCreationMessageConcurrentKafkaListenerContainerFactory")
    public void handleUserCreationEvent(AccountCreationMessage accountCreationMessage) {
        authorManagementService.createAuthor(accountCreationMessage.getAccountId(), accountCreationMessage.getUsername());
    }

    @KafkaListener(topics = "${jms.topic.user-lifecycle.update}", groupId = "project",
    containerFactory = "accountUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void handleUserUpdateEvent(AccountUpdateMessage accountUpdateMessage) {
        authorManagementService.updateAuthor(accountUpdateMessage.getAccountId(), accountUpdateMessage.getNewUsername());
    }
}
