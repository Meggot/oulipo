package com.user.streaming;

import com.common.models.dtos.AccountDto;
import com.common.models.dtos.MessageDto;
import com.common.models.messages.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.configuration.UserSource;
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
@EnableBinding(UserSource.class)
@Profile("!Test")
public class UserStreamer implements UserLifecycleStreamer {

    @Autowired
    private UserSource userSource;

    @Autowired
    private ObjectMapper objectMapper;

    public MessageHeaders messageHeaders = new MessageHeaders(Collections.singletonMap(MessageHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()));


    @Override
    public void sendAccountCreationMessage(Message<AccountDto> accountCreationMessage) {
        try {
            userSource.accountCreationOutput()
                    .send(MessageBuilder.createMessage(
                            objectMapper.writeValueAsString(accountCreationMessage),
                            messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAccountUpdateMessage(Message<AccountDto> accountUpdateMessage) {
        try {
            userSource.accountUpdateOutput()
                    .send(MessageBuilder.createMessage(
                            objectMapper.writeValueAsString(accountUpdateMessage),
                            messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageSentMessage(Message<MessageDto> messageSentMessage) {
        try {
            userSource.messageSentOutput()
                    .send(MessageBuilder.createMessage(
                            objectMapper.writeValueAsString(messageSentMessage),
                            messageHeaders));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
