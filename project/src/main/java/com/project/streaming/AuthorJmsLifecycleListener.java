// Copyright (c) 2019 Travelex Ltd

package com.project.streaming;

import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import com.project.services.AuthorManagementService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Profile("!Test")
@Component
public class AuthorJmsLifecycleListener implements AuthorLifecycleListener {

    @Autowired
    AuthorManagementService authorManagementService;

    @Override
    @KafkaListener(topics = "${jms.topic.user-lifecycle.creation}", groupId = "project",
            containerFactory = "accountCreationMessageConcurrentKafkaListenerContainerFactory")
    public void handleUserCreationEvent(AccountCreationMessage accountCreationMessage) {
        authorManagementService.createAuthor(accountCreationMessage.getAccountId(), accountCreationMessage.getUsername());
    }

    @Override
    @KafkaListener(topics = "${jms.topic.user-lifecycle.update}", groupId = "project",
    containerFactory = "accountUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void handleUserUpdateEvent(AccountUpdateMessage accountUpdateMessage) {
        authorManagementService.updateAuthor(accountUpdateMessage.getAccountId(), accountUpdateMessage.getNewUsername());
    }
}
