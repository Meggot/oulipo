// Copyright (c) 2019 Travelex Ltd

package com.project.dao.handlers;

import com.common.models.dtos.ProjectDto;
import com.common.models.messages.Message;
import com.common.models.messages.MessageType;
import com.project.controllers.assemblers.ProjectAssembler;
import com.project.dao.entites.Project;
import com.project.streaming.ProjectLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Slf4j
@Component
public class ProjectEventHandler {

    public static ProjectLifecycleStreamer lifecycleStreamer;

    public static ProjectAssembler projectAssembler;

    @Autowired
    public void setLifecycleStreamer(ProjectLifecycleStreamer projectLifecycleStreamer) {
        lifecycleStreamer = projectLifecycleStreamer;
    }

    @Autowired
    public void setProjectAssembler(ProjectAssembler projectAssembler) {
        ProjectEventHandler.projectAssembler = projectAssembler;
    }

    @PostPersist
    void onPostPersist(Project project) {
        ProjectDto projectDto = projectAssembler.toResource(project);
        Message<ProjectDto> projectDtoMessage = new Message<>(projectDto, project.getId(), MessageType.PROJECT_CREATION);
        lifecycleStreamer.sendProjectCreationMessage(projectDtoMessage);
    }

    @PostUpdate
    void onPreUpdate(Project project) {
        ProjectDto projectDto = projectAssembler.toResource(project);
        Message<ProjectDto> projectDtoMessage = new Message<>(projectDto, project.getId(), MessageType.PROJECT_UPDATE);
        lifecycleStreamer.sendProjectUpdateMessage(projectDtoMessage);
    }

}
