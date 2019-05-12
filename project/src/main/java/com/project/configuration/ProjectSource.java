package com.project.configuration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ProjectSource {

    @Output("stream-project-lifecycle-creation")
    MessageChannel projectCreationOutput();

    @Output("stream-project-lifecycle-creation_uppercase")
    MessageChannel projectCreationOutputUppercase();


}
