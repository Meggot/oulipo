// Copyright (c) 2019 Travelex Ltd

package com.project.streaming;

import com.common.models.messages.*;

public interface ProjectLifecycleStreamer {

    void sendProjectCreationMessage(ProjectCreationMessage accountCreationMessage);

    void sendProjectUpdateMessage(ProjectUpdateMessage accountUpdateMessage);

    void sendProjectPartCreationMessage(ProjectPartCreationMessage projectPartCreationMessage);

    void sendProjectPartUpdateMessage(ProjectPartUpdateMessage projectPartUpdateMessage);

    void sendProjectRoleCreationMessage(ProjectRoleCreationMessage projectRoleCreationMessage);

    void sendProjectRoleUpdateMessage(ProjectRoleUpdateMessage projectRoleUpdateMessage);

    void sendProjectTagCreationMessage(ProjectTagCreationMessage projectTagCreationMessage);

    void sendProjectTagUpdateMessage(ProjectTagUpdateMessage projectTagUpdateMessage);

    void sendCopyEditCreationMessage(CopyEditCreationMessage copyEditCreationMessage);

    void sendCopyEditUpdateMessage(CopyEditUpdateMesage copyEditUpdateMessage);
}