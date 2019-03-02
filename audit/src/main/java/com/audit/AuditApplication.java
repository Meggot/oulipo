package com.audit;// Copyright (c) 2019 Travelex Ltd

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class AuditApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AuditApplication.class);
        ConfigurableApplicationContext applicationContext = app.run(args);
        Environment env = applicationContext.getEnvironment();
        log.info(
                "\n-------------------------------------------------------\n"
                        + "\tApplication '{}' is runniQng!\n"
                        + "\tLocal:\t\t[http://localhost:{}]\n"
                        + "-------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"));
    }

}
