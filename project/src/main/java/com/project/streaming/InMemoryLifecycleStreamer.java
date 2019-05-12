package com.project.streaming;

import com.common.models.dtos.*;
import com.common.models.messages.*;
import com.project.services.AuthorManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("Test")
public class InMemoryLifecycleStreamer implements ProjectLifecycleStreamer, AuthorLifecycleListener {

    public Map<MessageType, Integer> messagesSentOfTypes = new HashMap<>();

    void process(MessageType type) {
        if (!messagesSentOfTypes.containsKey(type)) {
            messagesSentOfTypes.put(type, 1);
        } else {
            int count = messagesSentOfTypes.get(type);
            count++;
            messagesSentOfTypes.put(type, count);

        }
    }

    @Autowired
    private AuthorManagementService authorManagementService;

    @Override
    public void handleUserCreationEvent(Message<AccountDto> accountCreationMessage) {
        String username = accountCreationMessage.getBody().getUsername();
        Integer userId = accountCreationMessage.getBody().getIdField();
        authorManagementService.createAuthor(userId, username);
    }

    @Override
    public void handleUserUpdateEvent(Message<AccountDto> accountUpdateMessage) {
        String username = accountUpdateMessage.getBody().getUsername();
        Integer userId = accountUpdateMessage.getBody().getIdField();
        authorManagementService.updateAuthor(userId, username);
    }

    @Override
    public void sendProjectCreationMessage(Message<ProjectDto> message) {
        process(message.getType());
    }

    @Override
    public void sendProjectUpdateMessage(Message<ProjectDto> message) {
        process(message.getType());
    }

    @Override
    public void sendProjectPartCreationMessage(Message<ProjectPartDto> message) {
        process(message.getType());
    }

    @Override
    public void sendProjectPartUpdateMessage(Message<ProjectPartDto> message) {
        process(message.getType());
    }

    @Override
    public void sendProjectRoleCreationMessage(Message<AuthorProjectRoleDto> message) {
        process(message.getType());
    }

    @Override
    public void sendProjectRoleUpdateMessage(Message<AuthorProjectRoleDto> message) {
        process(message.getType());
    }

    @Override
    public void sendProjectTagCreationMessage(Message<ProjectTagDto> message) {
        process(message.getType());
    }

    @Override
    public void sendProjectTagUpdateMessage(Message<ProjectTagDto> message) {
        process(message.getType());
    }

    @Override
    public void sendCopyEditCreationMessage(Message<CopyEditDto> message) {
        process(message.getType());
    }

    @Override
    public void sendCopyEditUpdateMessage(Message<CopyEditDto> message) {
        process(message.getType());
    }
}
