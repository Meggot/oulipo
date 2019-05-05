package com.notify.configuration;// Copyright (c) 2019 Travelex Ltd

import com.common.models.messages.*;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@Profile("!Test")
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    public Map<String, Object> getDefaultListenerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notify");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProjectTagCreationMessage> projectTagCreationMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProjectTagCreationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(ProjectTagCreationMessage.class)));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProjectTagUpdateMessage> projectTagUpdateMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProjectTagUpdateMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(ProjectTagUpdateMessage.class)));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProjectPartCreationMessage> projectPartCreationMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProjectPartCreationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(ProjectPartCreationMessage.class)));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CopyEditCreationMessage> copyEditCreationMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CopyEditCreationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(CopyEditCreationMessage.class)));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CopyEditUpdateMesage> copyEditUpdateMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CopyEditUpdateMesage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(CopyEditUpdateMesage.class)));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProjectPartUpdateMessage> projectPartUpdateMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProjectPartUpdateMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(ProjectPartUpdateMessage.class)));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageSentMessage> messageSentMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageSentMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(MessageSentMessage.class)));
        return factory;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProjectUpdateMessage> projectUpdateMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProjectUpdateMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(ProjectUpdateMessage.class)));
        return factory;
    }
}


