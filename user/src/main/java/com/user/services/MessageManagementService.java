package com.user.services;

import com.common.models.requests.SendMessageRequest;
import com.user.dao.entites.Account;
import com.user.dao.entites.Message;
import com.user.dao.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class  MessageManagementService {

    @Autowired
    private MessageRepository repository;

    public Message handlePostMessage(SendMessageRequest sendMessageRequest, Account recipient, Account sender) {
        Message message = new Message();
        message.setValue(sendMessageRequest.getValue());
        recipient.addMessageReceived(message);
        sender.addMessageSent(message);
        message = repository.save(message);
        return message;
    }
}
