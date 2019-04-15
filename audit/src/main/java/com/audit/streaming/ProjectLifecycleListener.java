package com.audit.streaming;

import com.audit.dao.repository.AuditRepository;
import com.audit.factories.AuditFactory;
import com.common.models.messages.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private NewTopic projectLifecycleCreationTopic;

    @Autowired
    private NewTopic projectLifecycleUpdateTopic;

    @Autowired
    private NewTopic projectTagLifecycleCreationTopic;

    @Autowired
    private NewTopic projectTagLifecycleUpdateTopic;

    @Autowired
    private NewTopic projectPartLifecycleCreationTopic;

    @Autowired
    private NewTopic projectPartLifecycleUpdateTopic;


    @KafkaListener(topics = "${jms.topic.project-lifecycle.creation}", groupId = "audit",
            containerFactory = "projectCreationMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectCreationMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectLifecycleCreationTopic.name(), partition, message);
        auditRepository.save(AuditFactory.toAudit(message));
    }

    @KafkaListener(topics = "${jms.topic.project-lifecycle.update}", groupId = "audit",
            containerFactory = "projectUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectUpdateMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectLifecycleUpdateTopic.name(), partition, message);
        auditRepository.save(AuditFactory.toAudit(message));
    }

    @KafkaListener(topics = "${jms.topic.project-tag-lifecycle.creation}", groupId = "audit",
            containerFactory = "projectTagCreationMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectTagCreationMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectTagLifecycleCreationTopic.name(), partition, message);
        auditRepository.save(AuditFactory.toAudit(message));
    }


    @KafkaListener(topics = "${jms.topic.project-tag-lifecycle.update}", groupId = "audit",
            containerFactory = "projectTagUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectTagUpdateMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectTagLifecycleUpdateTopic.name(), partition, message);
        auditRepository.save(AuditFactory.toAudit(message));
    }


    @KafkaListener(topics = "${jms.topic.project-part-lifecycle.update}", groupId = "audit",
            containerFactory = "projectPartUpdateMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectPartUpdateMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectPartLifecycleUpdateTopic.name(), partition, message);
        auditRepository.save(AuditFactory.toAudit(message));
    }



    @KafkaListener(topics = "${jms.topic.project-part-lifecycle.creation}", groupId = "audit",
            containerFactory = "projectPartCreationMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload ProjectPartCreationMessage message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info(">[KAFKA] Received a message on {} in the partition [{}] contents: [{}]", projectPartLifecycleCreationTopic.name(), partition, message);
        auditRepository.save(AuditFactory.toAudit(message));
    }
}
