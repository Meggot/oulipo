// Copyright (c) 2019 Travelex Ltd

package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafka
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class);
    }
}
