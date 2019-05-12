package com.project.streaming;

import com.common.models.messages.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("Test")
public class InMemoryLifecycleStreamer implements ProjectLifecycleStreamer {

    public List<Object> messagesReceived = new ArrayList();

    @Override
    public void sendProjectCreationMessage(ProjectCreationMessage projectCreationMessage) {
        messagesReceived.add(projectCreationMessage);
    }

    @Override
    public void sendProjectUpdateMessage(ProjectUpdateMessage projectUpdateMessage) {
        messagesReceived.add(projectUpdateMessage);
    }

    @Override
    public void sendProjectPartCreationMessage(ProjectPartCreationMessage projectPartCreationMessage) {

    }

    @Override
    public void sendProjectPartUpdateMessage(ProjectPartUpdateMessage projectPartUpdateMessage) {

    }

    @Override
    public void sendProjectRoleCreationMessage(ProjectRoleCreationMessage projectRoleCreationMessage) {

    }

    @Override
    public void sendProjectRoleUpdateMessage(ProjectRoleUpdateMessage projectRoleUpdateMessage) {

    }

    @Override
    public void sendProjectTagCreationMessage(ProjectTagCreationMessage projectTagCreationMessage) {

    }

    @Override
    public void sendProjectTagUpdateMessage(ProjectTagUpdateMessage projectTagUpdateMessage) {

    }

    @Override
    public void sendCopyEditCreationMessage(CopyEditCreationMessage copyEditCreationMessage) {

    }

    @Override
    public void sendCopyEditUpdateMessage(CopyEditUpdateMesage copyEditUpdateMessage) {

    }

}
