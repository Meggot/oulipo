// Copyright (c) 2019 Travelex Ltd

package com.project.streaming;

import com.common.models.messages.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public KafkaTemplate<String, ProjectPartUpdateMessage> projectPartUpdateTemplate;

    @Autowired
    public KafkaTemplate<String, ProjectPartCreationMessage> projectPartCreationTemplate;

    @Autowired
    public KafkaTemplate<String, CopyEditUpdateMesage> copyEditUpdateTemplate;

    @Autowired
    public KafkaTemplate<String, CopyEditCreationMessage> copyEditCreationTemplate;

    @Autowired
    public KafkaTemplate<String, ProjectTagUpdateMessage> projectTagUpdateTemplate;

    @Autowired
    public KafkaTemplate<String, ProjectTagCreationMessage> projectTagCreationTemplate;

    @Autowired
    public KafkaTemplate<String, ProjectRoleUpdateMessage> projectRoleUpdateTemplate;

    @Autowired
    public KafkaTemplate<String, ProjectRoleCreationMessage> projectRoleCreationTemplate;


    @Autowired
    private NewTopic projectLifecycleCreationTopic;

    @Autowired
    private NewTopic projectLifecycleUpdateTopic;

    @Autowired
    public NewTopic projectPartLifecycleCreationTopic;

    @Autowired
    public NewTopic projectPartLifecycleUpdateTopic;

    @Autowired
    public NewTopic projectTagLifecycleCreationTopic;

    @Autowired
    public NewTopic projectTagLifecycleUpdateTopic;

    @Autowired
    public NewTopic projectRoleLifecycleCreationTopic;

    @Autowired
    public NewTopic projectRoleLifecycleUpdateTopic;

    @Autowired
    public NewTopic copyEditLifecycleCreationTopic;

    @Autowired
    public NewTopic copyEditLifecycleUpdateTopic;


    @Override
    public void sendProjectCreationMessage(ProjectCreationMessage projectCreationMessage) {
        ListenableFuture<SendResult<String, ProjectCreationMessage>> future = projectCreationTemplate.send(projectLifecycleCreationTopic.name(), projectCreationMessage);
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
        ListenableFuture<SendResult<String, ProjectUpdateMessage>> future = projectUpdateTemplate.send(projectLifecycleUpdateTopic.name(), projectUpdateMessage);
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

    @Override
    public void sendProjectPartCreationMessage(ProjectPartCreationMessage ProjectPartCreationMessage) {
        ListenableFuture<SendResult<String, ProjectPartCreationMessage>> future = projectPartCreationTemplate.send(projectPartLifecycleCreationTopic.name(), ProjectPartCreationMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ProjectPartCreationMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", ProjectPartCreationMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ProjectPartCreationMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", ProjectPartCreationMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

    @Override
    public void sendProjectPartUpdateMessage(ProjectPartUpdateMessage projectPartUpdateMessage) {
        ListenableFuture<SendResult<String, ProjectPartUpdateMessage>> future = projectPartUpdateTemplate.send(projectPartLifecycleUpdateTopic.name(), projectPartUpdateMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ProjectPartUpdateMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", projectPartUpdateMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ProjectPartUpdateMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", projectPartUpdateMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

    @Override
    public void sendProjectRoleCreationMessage(ProjectRoleCreationMessage projectRoleCreationMessage) {
        ListenableFuture<SendResult<String, ProjectRoleCreationMessage>> future = projectRoleCreationTemplate.send(projectRoleLifecycleCreationTopic.name(), projectRoleCreationMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ProjectRoleCreationMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", projectRoleCreationMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ProjectRoleCreationMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", projectRoleCreationMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

    @Override
    public void sendProjectRoleUpdateMessage(ProjectRoleUpdateMessage projectRoleUpdateMessage) {
        ListenableFuture<SendResult<String, ProjectRoleUpdateMessage>> future = projectRoleUpdateTemplate.send(projectRoleLifecycleUpdateTopic.name(), projectRoleUpdateMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ProjectRoleUpdateMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", projectRoleUpdateMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ProjectRoleUpdateMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", projectRoleUpdateMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

    @Override
    public void sendProjectTagCreationMessage(ProjectTagCreationMessage projectTagCreationMessage) {
        ListenableFuture<SendResult<String, ProjectTagCreationMessage>> future = projectTagCreationTemplate.send(projectTagLifecycleCreationTopic.name(), projectTagCreationMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ProjectTagCreationMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", projectTagCreationMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ProjectTagCreationMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", projectTagCreationMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

    @Override
    public void sendProjectTagUpdateMessage(ProjectTagUpdateMessage projectTagUpdateMessage) {
        ListenableFuture<SendResult<String, ProjectTagUpdateMessage>> future = projectTagUpdateTemplate.send(projectTagLifecycleUpdateTopic.name(), projectTagUpdateMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ProjectTagUpdateMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", projectTagUpdateMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, ProjectTagUpdateMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", projectTagUpdateMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

    @Override
    public void sendCopyEditCreationMessage(CopyEditCreationMessage copyEditCreationMessage) {
        ListenableFuture<SendResult<String, CopyEditCreationMessage>> future = copyEditCreationTemplate.send(copyEditLifecycleCreationTopic.name(), copyEditCreationMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, CopyEditCreationMessage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", copyEditCreationMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, CopyEditCreationMessage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", copyEditCreationMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }

    @Override
    public void sendCopyEditUpdateMessage(CopyEditUpdateMesage copyEditUpdateMessage) {
        ListenableFuture<SendResult<String, CopyEditUpdateMesage>> future = copyEditUpdateTemplate.send(copyEditLifecycleUpdateTopic.name(), copyEditUpdateMessage);
        future.addCallback(new ListenableFutureCallback<SendResult<String, CopyEditUpdateMesage>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("> [KAFKA] Failed to send message [{}] due to {}", copyEditUpdateMessage, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, CopyEditUpdateMesage> stringStringSendResult) {
                log.info("> [KAFKA] Successfully sent message [{}] with offset [{}]", copyEditUpdateMessage, stringStringSendResult.getRecordMetadata().offset());
            }
        });
    }


}
