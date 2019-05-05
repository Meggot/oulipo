package com.notify.streaming;

import com.common.models.messages.MessageSentMessage;
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
public class AccountJmsLifecycleStreamer implements AccountLifecycleStreamer{

    @Autowired
    NewTopic messageTopic;

    @Autowired
    NotificationManagementService managementService;

    @Autowired
    NotificationsFactory factory;

    @Override
    @KafkaListener(topics = "${jms.topic.message.sent}", groupId = "notify",
            containerFactory = "messageSentMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload MessageSentMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", messageTopic.name(), partition, message);
        managementService.handleNotification(factory.toNotification(message));
    }

}
