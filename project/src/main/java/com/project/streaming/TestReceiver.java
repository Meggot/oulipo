package com.project.streaming;

import com.common.models.messages.ProjectCreationMessage;
import com.project.configuration.ProjectSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableBinding(ProjectSource.class)
@Profile( "!Test")
public class TestReceiver {

    @StreamListener(value = "stream-project-lifecycle-creation_uppercase")
    public void logProjectCreationUppercase(ProjectCreationMessage creationMessage) {
        System.out.println("ReCIEVED: " + creationMessage);
    }


    @StreamListener(value = "stream-project-lifecycle-creation")
    @SendTo("stream-project-lifecycle-creation_uppercase")
    public ProjectCreationMessage uppercaseProjectCreation(ProjectCreationMessage creationMessage) {
        creationMessage.setTitle(creationMessage.getTitle().toUpperCase());
        return creationMessage;
    }
}
