package com.notify.streaming;

import com.common.models.messages.CopyEditCreationMessage;
import com.common.models.messages.CopyEditUpdateMesage;
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
public class CopyJmsLifecycleListener implements CopyLifecycleStreamer{

    @Autowired
    NotificationsFactory notificationsFactory;

    @Autowired
    NotificationManagementService notificationManagementService;

    @Autowired
    NewTopic copyEditLifecycleCreationTopic;

    @Autowired
    NewTopic copyEditLifecycleUpdateTopic;



    @Override
    @KafkaListener(topics = "${jms.topic.copy-edit-lifecycle.update}", groupId = "notify",
            containerFactory = "copyEditUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload CopyEditUpdateMesage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", copyEditLifecycleUpdateTopic.name(), partition, message);
        notificationManagementService.handleNotification(notificationsFactory.toNotification(message));
    }

    @Override
    @KafkaListener(topics = "${jms.topic.copy-edit-lifecycle.creation}", groupId = "notify",
            containerFactory = "copyEditCreationMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload CopyEditCreationMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", copyEditLifecycleCreationTopic.name(), partition, message);
        notificationManagementService.handleNotification(notificationsFactory.toNotification(message));
    }


}
