// Copyright (c) 2019 Travelex Ltd

package com.project.streaming;

import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import com.common.models.messages.ProjectCreationMessage;
import com.common.models.messages.ProjectUpdateMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
@Primary
@Profile("!Test")
public class JmsProjectLifecycleStreamer implements ProjectLifecycleStreamer {

    @Autowired
    private KafkaTemplate<String, ProjectCreationMessage> projectCreationTemplate;

    @Autowired
    private KafkaTemplate<String, ProjectUpdateMessage> projectUpdateTemplate;


    @Value("${jms.topic.project-lifecycle.creation}")
    private String projectLifecycleCreation;

    @Value("${jms.topic.project-lifecycle.update}")
    private String projectLifecycleUpdate;

    @Override
    public void sendProjectCreationMessage(ProjectCreationMessage projectCreationMessage) {
        ListenableFuture<SendResult<String, ProjectCreationMessage>> future = projectCreationTemplate.send(projectLifecycleCreation, projectCreationMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ProjectCreationMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", projectCreationMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ProjectCreationMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", projectCreationMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

    @Override
    public void sendProjectUpdateMessage(ProjectUpdateMessage projectUpdateMessage) {
        ListenableFuture<SendResult<String, ProjectUpdateMessage>> future = projectUpdateTemplate.send(projectLifecycleUpdate, projectUpdateMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ProjectUpdateMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", projectUpdateMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ProjectUpdateMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", projectUpdateMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

}
