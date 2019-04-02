// Copyright (c) 2019 Travelex Ltd

package com.project.configuration;

import com.common.models.messages.*;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

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

    private Map<String, Object> getDefaultProducerProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }


    public ConsumerFactory<String, AccountCreationMessage> accountCreationConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "project");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(AccountCreationMessage.class));
    }

    public ConsumerFactory<String, AccountUpdateMessage> accountUpdateConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "project");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(AccountUpdateMessage.class));
    }



    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountCreationMessage> accountCreationMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AccountCreationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(accountCreationConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountUpdateMessage> accountUpdateMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AccountUpdateMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(accountUpdateConsumerFactory());
        return factory;
    }

    @Bean
    public KafkaTemplate<String, ProjectUpdateMessage> projectUpdateTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getDefaultProducerProperties()));
    }

    @Bean
    public KafkaTemplate<String, ProjectCreationMessage> projectCreationTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getDefaultProducerProperties()));
    }

    @Bean
    public KafkaTemplate<String, ProjectPartUpdateMessage> projectPartUpdateTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getDefaultProducerProperties()));
    }

    @Bean
    public KafkaTemplate<String, ProjectPartCreationMessage> projectPartCreationTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getDefaultProducerProperties()));
    }


    @Bean
    public KafkaTemplate<String, CopyEditUpdateMesage> copyEditUpdateTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getDefaultProducerProperties()));
    }

    @Bean
    public KafkaTemplate<String, CopyEditCreationMessage> copyEditCreationTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getDefaultProducerProperties()));
    }

    @Bean
    public KafkaTemplate<String, ProjectTagUpdateMessage> projectTagUpdateTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getDefaultProducerProperties()));
    }

    @Bean
    public KafkaTemplate<String, ProjectTagCreationMessage> projectTagCreationTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getDefaultProducerProperties()));
    }

    @Bean
    public KafkaTemplate<String, ProjectRoleUpdateMessage> projectRoleUpdateTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getDefaultProducerProperties()));
    }

    @Bean
    public KafkaTemplate<String, ProjectRoleCreationMessage> projectRoleCreationTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getDefaultProducerProperties()));
    }

}
