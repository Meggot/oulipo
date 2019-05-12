// Copyright (c) 2019 Travelex Ltd

package com.project.dao.handlers;

import com.common.models.dtos.ProjectDto;
import com.common.models.dtos.ProjectPartDto;
import com.common.models.messages.Message;
import com.common.models.messages.MessageType;
import com.project.controllers.assemblers.PartAssembler;
import com.project.controllers.assemblers.ProjectAssembler;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectPart;
import com.project.streaming.ProjectLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Slf4j
@Component
public class PartEventHandler {

    public static ProjectLifecycleStreamer lifecycleStreamer;

    public static PartAssembler partAssembler;

    @Autowired
    public void setLifecycleStreamer(ProjectLifecycleStreamer projectLifecycleStreamer) {
        this.lifecycleStreamer = projectLifecycleStreamer;
    }

    @Autowired
    public void setPartAssembler(PartAssembler partAssembler) {
        this.partAssembler = partAssembler;
    }

    @PostPersist
    void onPostPersist(ProjectPart part) {
        ProjectPartDto projectDto = partAssembler.toResource(part);
        Message<ProjectPartDto> projectDtoMessage = new Message<>(projectDto, part.getProject().getId(), MessageType.PROJECT_PART_CREATION);
        lifecycleStreamer.sendProjectPartCreationMessage(projectDtoMessage);
    }

    @PostUpdate
    void onPreUpdate(ProjectPart part) {
        ProjectPartDto projectDto = partAssembler.toResource(part);
        Message<ProjectPartDto> partDto = new Message<>(projectDto, part.getProject().getId(), MessageType.PROJECT_PART_UPDATE);
        lifecycleStreamer.sendProjectPartUpdateMessage(partDto);
    }

}
