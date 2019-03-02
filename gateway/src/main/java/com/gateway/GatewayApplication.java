// Copyright (c) 2019 Travelex Ltd

package com.gateway;

import com.gateway.filters.pre.PreFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients
@EnableWebSecurity
@Slf4j
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatewayApplication.class);
        ConfigurableApplicationContext applicationContext = app.run(args);
        Environment env = applicationContext.getEnvironment();
        log.info(
                "\n-------------------------------------------------------\n"
                        + "\tApplication '{}' is running!\n"
                        + "\tLocal:\t\t[http://localhost:{}]\n"
                        + "-------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"));
    }

    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }
}

