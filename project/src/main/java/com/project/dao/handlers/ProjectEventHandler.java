// Copyright (c) 2019 Travelex Ltd

package com.project.dao.handlers;

import com.common.models.messages.ProjectCreationMessage;
import com.common.models.messages.ProjectUpdateMessage;
import com.project.dao.entites.Project;
import com.project.streaming.ProjectLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Slf4j
@Component
public class ProjectEventHandler {

    public static ProjectLifecycleStreamer lifecycleStreamer;

    @Autowired
    public void setLifecycleStreamer(ProjectLifecycleStreamer projectLifecycleStreamer) {
        this.lifecycleStreamer = projectLifecycleStreamer;
    }

    @PrePersist
    void onPrePersist(Project project) {
        log.info("HANDLING PROJECT CREATION {}", project);
        ProjectCreationMessage projectCreationMessage = new ProjectCreationMessage();
        projectCreationMessage.setProjectId(String.valueOf(project.getId()));
        projectCreationMessage.setTitle(project.getTitle());
        projectCreationMessage.setUserId(project.getOriginalAuthor().getUserId().toString());
        projectCreationMessage.setSynopsis(project.getSynopsis());
        lifecycleStreamer.sendProjectCreationMessage(projectCreationMessage);
    }

    @PreUpdate
    void onPreUpdate(Project project) {
        log.info("HANDLING PROJECT UPDATE {}", project);
        ProjectUpdateMessage projectUpdateMessage = new ProjectUpdateMessage();
        projectUpdateMessage.setNewSynopsis(project.getSynopsis());
        projectUpdateMessage.setNewTitle(project.getTitle());
        projectUpdateMessage.setUserId(project.getOriginalAuthor().getUserId().toString());
        lifecycleStreamer.sendProjectUpdateMessage(projectUpdateMessage);
    }

}
