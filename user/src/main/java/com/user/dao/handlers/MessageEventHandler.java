package com.user.dao.handlers;

import com.common.models.dtos.MessageDto;
import com.common.models.messages.MessageType;
import com.user.controllers.assemblers.MessageAssembler;
import com.user.dao.entites.Message;
import com.user.streaming.UserLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;

@Component
@Slf4j
public class MessageEventHandler {

    public static UserLifecycleStreamer userLifecycleStreamer;

    public static MessageAssembler assembler;

    @Autowired
    public void setUserLifecycleStreamer(UserLifecycleStreamer userLifecycleStreamer) {
        MessageEventHandler.userLifecycleStreamer = userLifecycleStreamer;
    }

    @Autowired
    public void setMessageAssembler(MessageAssembler messageAssembler) {
        assembler = messageAssembler;
    }

    @PostPersist
    public void onPostPersist(Message message) {
        MessageDto dto = assembler.toResource(message);
        com.common.models.messages.Message<MessageDto> messageStream =
                new com.common.models.messages.Message(dto, dto.getToUserId(), MessageType.MESSAGE_SENT);
        userLifecycleStreamer.sendMessageSentMessage(messageStream);
    }
}