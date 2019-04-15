// Copyright (c) 2019 Travelex Ltd

package com.audit.configs;

import com.common.models.messages.*;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
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
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "audit");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    public ConsumerFactory<String, AccountUpdateMessage> accountUpdateConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "audit");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(AccountUpdateMessage.class));
    }

    public ConsumerFactory<String, AccountCreationMessage> accountCreationConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "audit");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(AccountCreationMessage.class));
    }

    public ConsumerFactory<String, ProjectCreationMessage> projectCreationConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "audit");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(ProjectCreationMessage.class));
    }

    public ConsumerFactory<String, ProjectUpdateMessage> projectUpdateConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "audit");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(ProjectUpdateMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountCreationMessage> accountCreationMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AccountCreationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(AccountCreationMessage.class)));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountUpdateMessage> accountUpdateMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AccountUpdateMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(AccountUpdateMessage.class)));
        return factory;
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
    public ConcurrentKafkaListenerContainerFactory<String, ProjectPartUpdateMessage> projectPartUpdateMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProjectPartUpdateMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(ProjectPartUpdateMessage.class)));
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProjectCreationMessage> projectCreationMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProjectCreationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getDefaultListenerProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(ProjectCreationMessage.class)));
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


