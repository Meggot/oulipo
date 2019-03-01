// Copyright (c) 2019 Travelex Ltd

package com.user.streaming;

import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Role;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
@Profile("Dev")
public class JmsUserLifecycleStreamer implements UserLifecycleStreamer{

    @Autowired
    private KafkaTemplate<String, AccountCreationMessage> accountCreationTemplate;

    @Autowired
    private KafkaTemplate<String, AccountUpdateMessage> accountUpdateTemplate;

    @Value("${jms.topic.user-lifecycle.creation}")
    private String userLifecycleCreationTopic;

    @Value("${jms.topic.user-lifecycle.update}")
    private String userLifecycleUpdateTopic;

    @Override
    public void sendAccountCreationMessage(AccountCreationMessage accountCreationMessage) {
        ListenableFuture<SendResult<String, AccountCreationMessage>> future = accountCreationTemplate.send(userLifecycleCreationTopic, accountCreationMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, AccountCreationMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", accountCreationMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, AccountCreationMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", accountCreationMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

    @Override
    public void sendAccountUpdateMessage(AccountUpdateMessage accountUpdateMessage) {
        ListenableFuture<SendResult<String, AccountUpdateMessage>> future = accountUpdateTemplate.send(userLifecycleUpdateTopic, accountUpdateMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, AccountUpdateMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", accountUpdateMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, AccountUpdateMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", accountUpdateMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

}