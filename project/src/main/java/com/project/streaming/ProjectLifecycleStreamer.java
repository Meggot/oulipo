// Copyright (c) 2019 Travelex Ltd

package com.project.streaming;

import com.common.models.messages.AccountCreationMessage;
import com.common.models.messages.AccountUpdateMessage;
import com.common.models.messages.ProjectCreationMessage;
import com.common.models.messages.ProjectUpdateMessage;

public interface ProjectLifecycleStreamer {

    void sendProjectCreationMessage(ProjectCreationMessage accountCreationMessage);

    void sendProjectUpdateMessage(ProjectUpdateMessage accountUpdateMessage);
}