// Copyright (c) 2019 Travelex Ltd

package com.notify.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.StreamsBuilderFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@Profile("!Test")
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private String applicationId = "notify-streams";

    @Bean(name = "defaultKafkaStreamsConfig")
    public StreamsConfig streamsConfig() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return new StreamsConfig(props);
    }

    @Bean
    public StreamsBuilderFactoryBean myKStreamBuilder(@Autowired StreamsConfig streamsConfig) {
        return new StreamsBuilderFactoryBean(streamsConfig);
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

}
