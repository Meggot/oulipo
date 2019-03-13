package com.audit.streaming;

import com.audit.dao.repository.AuditRepository;
import com.audit.factories.AuditFactory;
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

@Component
@Slf4j
public class ProjectLifecycleListener {

    @Autowired
    AuditRepository auditRepository;

    @Value("${jms.topic.user-lifecycle.creation}")
    private String projectLifecycleCreationTopic;

    @Value("${jms.topic.user-lifecycle.update}")
    private String projectLifecycleUpdateTopic;

    @KafkaListener(topics = "${jms.topic.project-lifecycle.creation}", groupId = "audit",
            containerFactory = "projectCreationMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectCreationMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectLifecycleCreationTopic, partition, message);
        auditRepository.save(AuditFactory.toAudit(message));
    }

    @KafkaListener(topics = "${jms.topic.project-lifecycle.update}", groupId = "audit",
            containerFactory = "projectUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectUpdateMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectLifecycleUpdateTopic, partition, message);
        auditRepository.save(AuditFactory.toAudit(message));
    }
}
