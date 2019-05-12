package com.project.streaming;

import com.common.models.messages.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.configuration.ProjectSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
@EnableBinding(ProjectSource.class)
@Profile( "!Test")
public class ProjectStreamer implements AuthorLifecycleListener, ProjectLifecycleStreamer{

    @Autowired
    private ProjectSource processor;

    @Autowired
    private ObjectMapper objectMapper;

    public MessageHeaders messageHeaders = new MessageHeaders(Collections.singletonMap(MessageHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));


    @Override
    public void handleUserCreationEvent(AccountCreationMessage accountCreationMessage) {


    }

    @Override
    public void handleUserUpdateEvent(AccountUpdateMessage accountUpdateMessage) {

    }

    @Override
    public void sendProjectCreationMessage(ProjectCreationMessage projectCreationMessage) {
        try {
            processor.projectCreationOutput()
                    .send(MessageBuilder.createMessage(objectMapper.writeValueAsString(projectCreationMessage), messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendProjectUpdateMessage(ProjectUpdateMessage accountUpdateMessage) {

    }

    @Override
    public void sendProjectPartCreationMessage(ProjectPartCreationMessage projectPartCreationMessage) {

    }

    @Override
    public void sendProjectPartUpdateMessage(ProjectPartUpdateMessage projectPartUpdateMessage) {

    }

    @Override
    public void sendProjectRoleCreationMessage(ProjectRoleCreationMessage projectRoleCreationMessage) {

    }

    @Override
    public void sendProjectRoleUpdateMessage(ProjectRoleUpdateMessage projectRoleUpdateMessage) {

    }

    @Override
    public void sendProjectTagCreationMessage(ProjectTagCreationMessage projectTagCreationMessage) {

    }

    @Override
    public void sendProjectTagUpdateMessage(ProjectTagUpdateMessage projectTagUpdateMessage) {

    }

    @Override
    public void sendCopyEditCreationMessage(CopyEditCreationMessage copyEditCreationMessage) {

    }

    @Override
    public void sendCopyEditUpdateMessage(CopyEditUpdateMesage copyEditUpdateMessage) {

    }

}
