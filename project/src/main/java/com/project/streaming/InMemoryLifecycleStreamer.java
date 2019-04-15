package com.project.streaming;

import com.common.models.messages.*;
import org.springframework.stereotype.Component;

@Component
public class InMemoryLifecycleStreamer implements ProjectLifecycleStreamer {

    @Override
    public void sendProjectCreationMessage(ProjectCreationMessage accountCreationMessage) {

    }

    @Override
    public void sendProjectUpdateMessage(ProjectUpdateMessage accountUpdateMessage) {

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
