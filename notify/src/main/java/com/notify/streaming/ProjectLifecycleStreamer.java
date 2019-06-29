package com.notify.streaming;

import com.common.models.dtos.ProjectDto;
import com.common.models.dtos.ProjectPartDto;
import com.common.models.dtos.ProjectTagDto;
import com.common.models.messages.Message;

public interface ProjectLifecycleStreamer {

    void handleProjectTagCreation(Message<ProjectTagDto> message);
    void handleProjectTagUpdate(Message<ProjectTagDto> message);
    void handleProjectPartUpdate(Message<ProjectPartDto> message);
    void handleProjectPartCreation(Message<ProjectPartDto> message);
    void handleProjectUpdate(Message<ProjectDto> message);

}
