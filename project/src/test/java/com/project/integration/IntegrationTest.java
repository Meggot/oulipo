package com.project.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@EmbeddedKafka(partitions = 1, controlledShutdown = true,
        brokerProperties = {"listeners=PLAINTEXT://localhost:12999", "port=12999"})
public class IntegrationTest {

    @Autowired
    public static KafkaEmbedded embeddedKafka;
}
