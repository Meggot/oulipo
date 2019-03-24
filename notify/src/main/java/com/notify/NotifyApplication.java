package com.notify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class NotifyApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NotifyApplication.class);
        ConfigurableApplicationContext applicationContext = app.run(args);
        Environment env = applicationContext.getEnvironment();
//        log.info(
//                "\n-------------------------------------------------------\n"
//                        + "\tApplication '{}' is running!\n"
//                        + "\tLocal:\t\t[http://localhost:{}]\n"
//                        + "-------------------------------------------------------",
//                env.getProperty("spring.application.name"),
//                env.getProperty("server.port"));
    }

}