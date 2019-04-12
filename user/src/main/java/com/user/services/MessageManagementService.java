package com.user.services;

import com.common.models.messages.MessageSentMessage;
import com.common.models.requests.SendMessageRequest;
import com.user.dao.entites.Account;
import com.user.dao.entites.Message;
import com.user.dao.repository.MessageRepository;
import com.user.streaming.UserLifecycleStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageManagementService {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private UserLifecycleStreamer userLifecycleStreamer;

    public Message handlePostMessage(SendMessageRequest sendMessageRequest, Account recipient, Account sender) {
        Message message = new Message();
        message.setValue(sendMessageRequest.getValue());
        recipient.addMessageReceived(message);
        sender.addMessageSent(message);
        message = repository.save(message);
        MessageSentMessage messageSentMessage = new MessageSentMessage();
        messageSentMessage.setFromUsername(sender.getUsername());
        messageSentMessage.setSentTime(message.getCreationDate().toString());
        messageSentMessage.setToUsername(recipient.getUsername());
        messageSentMessage.setValue(message.getValue());
        userLifecycleStreamer.sendMessageSentMessage(messageSentMessage);
        return message;
    }
}
