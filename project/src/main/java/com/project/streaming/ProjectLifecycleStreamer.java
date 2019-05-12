// Copyright (c) 2019 Travelex Ltd

package com.project.streaming;

import com.common.models.dtos.*;
import com.common.models.messages.*;

public interface ProjectLifecycleStreamer {

    void sendProjectCreationMessage(Message<ProjectDto> message);

    void sendProjectUpdateMessage(Message<ProjectDto> message);

    void sendProjectPartCreationMessage(Message<ProjectPartDto> message);

    void sendProjectPartUpdateMessage(Message<ProjectPartDto> message);

    void sendProjectRoleCreationMessage(Message<AuthorProjectRoleDto> message);

    void sendProjectRoleUpdateMessage(Message<AuthorProjectRoleDto> message);

    void sendProjectTagCreationMessage(Message<ProjectTagDto> message);

    void sendProjectTagUpdateMessage(Message<ProjectTagDto> message);

    void sendCopyEditCreationMessage(Message<CopyEditDto> message);

    void sendCopyEditUpdateMessage(Message<CopyEditDto> message);
}