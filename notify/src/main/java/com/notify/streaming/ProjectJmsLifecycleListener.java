// Copyright (c) 2019 Travelex Ltd

package com.notify.streaming;

import com.common.models.messages.*;
import com.notify.services.NotificationManagementService;
import com.notify.services.NotificationsFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("!Test")
public class ProjectJmsLifecycleListener implements ProjectLifecycleStreamer {

    @Autowired
    NotificationsFactory notificationsFactory;

    @Autowired
    NotificationManagementService notificationManagementService;

    @Autowired
    NewTopic projectTagLifecycleCreationTopic;

    @Autowired
    NewTopic projectTagLifecycleUpdateTopic;

    @Autowired
    NewTopic projectPartLifecycleUpdateTopic;

    @Autowired
    NewTopic projectPartLifecycleCreationTopic;

    @Autowired
    NewTopic projectLifecycleUpdateTopic;


    @Override
    @KafkaListener(topics = "${jms.topic.project-tag-lifecycle.creation}", groupId = "notify",
            containerFactory = "projectTagCreationMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectTagCreationMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectTagLifecycleCreationTopic.name(), partition, message);
        notificationManagementService.handleNotification(notificationsFactory.toNotification(message));
    }

    @Override
    @KafkaListener(topics = "${jms.topic.project-tag-lifecycle.update}", groupId = "notify",
            containerFactory = "projectTagUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectTagUpdateMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectTagLifecycleUpdateTopic.name(), partition, message);
        notificationManagementService.handleNotification(notificationsFactory.toNotification(message));
    }


    @Override
    @KafkaListener(topics = "${jms.topic.project-part-lifecycle.update}", groupId = "notify",
            containerFactory = "projectPartUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectPartUpdateMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectPartLifecycleUpdateTopic.name(), partition, message);
        notificationManagementService.handleNotification(notificationsFactory.toNotification(message));
    }

    @Override
    @KafkaListener(topics = "${jms.topic.project-lifecycle.update}", groupId = "notify",
            containerFactory = "projectUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectUpdateMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectLifecycleUpdateTopic.name(), partition, message);
        notificationManagementService.handleNotification(notificationsFactory.toNotification(message));
    }


    @KafkaListener(topics = "${jms.topic.project-part-lifecycle.creation}", groupId = "notify",
            containerFactory = "projectPartCreationMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectPartCreationMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectPartLifecycleCreationTopic.name(), partition, message);
        notificationManagementService.handleNotification(notificationsFactory.toNotification(message));
    }

}