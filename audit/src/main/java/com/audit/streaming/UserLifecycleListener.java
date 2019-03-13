// Copyright (c) 2019 Travelex Ltd

package com.audit.streaming;

import com.audit.dao.entites.Audit;
import com.audit.dao.repository.AuditRepository;
import com.audit.factories.AuditFactory;
import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import com.common.models.messages.ProjectCreationMessage;
import com.common.models.messages.ProjectUpdateMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@Slf4j
public class UserLifecycleListener {

    @Autowired
    AuditRepository auditRepository;

    @Value("${jms.topic.user-lifecycle.creation}")
    private String userLifecycleCreationTopic;

    @Value("${jms.topic.user-lifecycle.update}")
    private String userLifecycleUpdateTopic;

    @KafkaListener(topics = "${jms.topic.user-lifecycle.creation}", groupId = "audit",
            containerFactory = "accountCreationMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload AccountCreationMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on 'topic-user-lifecycle-creation' in the partition [{}] contents: [{}]", partition, message);
        auditRepository.save(AuditFactory.toAudit(message));
    }
    @KafkaListener(topics = "${jms.topic.user-lifecycle.update}", groupId = "audit",
            containerFactory = "accountUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload AccountUpdateMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on 'topic-user-lifecycle-update' in the partition [{}] contents: [{}]", partition, message);
        auditRepository.save(AuditFactory.toAudit(message));
    }

}