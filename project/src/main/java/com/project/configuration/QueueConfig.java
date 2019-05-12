package com.project.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile( "!Test")
public class QueueConfig {

    @Value("${jms.topic.user-lifecycle.creation}")
    private String userLifecycleCreationTopic;

    @Value("${jms.topic.user-lifecycle.update}")
    private String userLifecycleUpdateTopic;

    @Value("${jms.topic.project-lifecycle.creation}")
    private String projectLifecycleCreationTopic;

    @Value("${jms.topic.project-lifecycle.update}")
    private String projectLifecycleUpdateTopic;

    @Value("${jms.topic.project-part-lifecycle.creation}")
    private String projectPartLifecycleCreationTopic;

    @Value("${jms.topic.project-part-lifecycle.update}")
    private String projectPartLifecycleUpdateTopic;

    @Value("${jms.topic.project-tag-lifecycle.creation}")
    private String projectTagLifecycleCreationTopic;

    @Value("${jms.topic.project-tag-lifecycle.update}")
    private String projectTagLifecycleUpdateTopic;

    @Value("${jms.topic.project-role-lifecycle.creation}")
    private String projectRoleLifecycleCreationTopic;

    @Value("${jms.topic.project-role-lifecycle.update}")
    private String projectRoleLifecycleUpdateTopic;

    @Value("${jms.topic.copy-edit-lifecycle.creation}")
    private String copyEditLifecycleCreationTopic;

    @Value("${jms.topic.copy-edit-lifecycle.update}")
    private String copyEditLifecycleUpdateTopic;

    @Bean
    public NewTopic projectPartLifecycleCreationTopic() {
        return new NewTopic(projectPartLifecycleCreationTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic projectPartLifecycleUpdateTopic() {
        return new NewTopic(projectPartLifecycleUpdateTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic projectTagLifecycleCreationTopic() {
        return new NewTopic(projectTagLifecycleCreationTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic projectTagLifecycleUpdateTopic() {
        return new NewTopic(projectTagLifecycleUpdateTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic projectRoleLifecycleCreationTopic() {
        return new NewTopic(projectRoleLifecycleCreationTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic projectRoleLifecycleUpdateTopic() {
        return new NewTopic(projectRoleLifecycleUpdateTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic copyEditLifecycleCreationTopic() {
        return new NewTopic(copyEditLifecycleCreationTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic copyEditLifecycleUpdateTopic() {
        return new NewTopic(copyEditLifecycleUpdateTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic projectLifecycleCreationTopic() {
        return new NewTopic(projectLifecycleCreationTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic projectLifecycleUpdateTopic() {
        return new NewTopic(projectLifecycleUpdateTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic userLifecycleCreationTopic() {
        return new NewTopic(userLifecycleCreationTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic userLifecycleUpdateTopic() {
        return new NewTopic(userLifecycleUpdateTopic, 1, (short) 1);
    }

}
