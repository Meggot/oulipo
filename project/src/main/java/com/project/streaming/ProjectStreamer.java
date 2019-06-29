package com.project.streaming;

import com.common.models.dtos.*;
import com.common.models.messages.Message;
import com.common.models.requests.CreateTagRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.configuration.ProjectSource;
import com.project.services.AuthorManagementService;
import com.project.services.ProjectTagManagementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
@EnableBinding(ProjectSource.class)
@Profile( "!Test")
public class ProjectStreamer implements AuthorLifecycleListener, ProjectLifecycleStreamer, SystemListener{

    @Autowired
    private ProjectSource processor;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorManagementService authorManagementService;

    @Autowired
    private ProjectTagManagementService projectTagManagementService;

    public MessageHeaders messageHeaders = new MessageHeaders(Collections.singletonMap(MessageHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));

    @Override
    @StreamListener(value = "account-creation")
    public void handleUserCreationEvent(Message<AccountDto> accountCreationMessage) {
        String username = accountCreationMessage.getBody().getUsername();
        Integer userId = accountCreationMessage.getBody().getIdField();
        authorManagementService.createAuthor(userId, username);
    }

    @Override
    @StreamListener(value = "account-update")
    public void handleUserUpdateEvent(Message<AccountDto> accountUpdateMessage) {
        String username = accountUpdateMessage.getBody().getUsername();
        Integer userId = accountUpdateMessage.getBody().getIdField();
        authorManagementService.updateAuthor(userId, username);
    }


    @Override
    @StreamListener(value = "system-add-tag")
    public void handleSystemAddTag(Message<CreateTagRequest> addTagRequest) {
        log.info("Received system add tag request {} ", addTagRequest);
        projectTagManagementService.handleSystemAddTag(addTagRequest.getBody(), addTagRequest.getEntityId());
    }

    @Override
    public void sendProjectCreationMessage(Message<ProjectDto> message) {
        try {
            processor.projectCreationOutput()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(message), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendProjectUpdateMessage(Message<ProjectDto> message) {
        try {
            processor.projectUpdateOutput()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(message), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendProjectPartCreationMessage(Message<ProjectPartDto> message) {
        try {
            processor.copyPartCreation()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(message), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendProjectPartUpdateMessage(Message<ProjectPartDto> message) {
        try {
            processor.copyPartUpdate()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(message), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendProjectRoleCreationMessage(Message<AuthorProjectRoleDto> message) {
        try {
            processor.projectRoleCreation()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(message), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendProjectRoleUpdateMessage(Message<AuthorProjectRoleDto> message) {
        try {
            processor.projectRoleUpdate()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(message), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendProjectTagCreationMessage(Message<ProjectTagDto> message) {
        try {
            processor.projectTagCreation()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(message), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendProjectTagUpdateMessage(Message<ProjectTagDto> message) {
        try {
            processor.projectTagUpdate()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(message), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCopyEditCreationMessage(Message<CopyEditDto> message) {
        try {
            processor.copyEditCreation()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(message), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCopyEditUpdateMessage(Message<CopyEditDto> message) {
        try {
            processor.copyEditUpdate()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(message), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
