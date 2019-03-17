package com.project.streaming;

import com.common.models.messages.ProjectCreationMessage;
import com.common.models.messages.ProjectUpdateMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("Test")
public class InMemoryLifecycleStreamer implements ProjectLifecycleStreamer {

    @Override
    public void sendProjectCreationMessage(ProjectCreationMessage accountCreationMessage) {

    }

    @Override
    public void sendProjectUpdateMessage(ProjectUpdateMessage accountUpdateMessage) {

    }

}
