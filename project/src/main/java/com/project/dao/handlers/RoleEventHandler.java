package com.project.dao.handlers;

import com.common.models.dtos.AuthorProjectRoleDto;
import com.common.models.messages.Message;
import com.common.models.messages.MessageType;
import com.project.controllers.assemblers.AuthorProjectRoleAssembler;
import com.project.dao.entites.AuthorProjectRole;
import com.project.streaming.ProjectLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Component
@Slf4j
public class RoleEventHandler {

    public static ProjectLifecycleStreamer lifecycleStreamer;

    public static AuthorProjectRoleAssembler assembler;

    @Autowired
    public void setLifecycleStreamer(ProjectLifecycleStreamer projectLifecycleStreamer) {
        lifecycleStreamer = projectLifecycleStreamer;
    }

    @Autowired
    public void setAuthorProjectRoleAssembler(AuthorProjectRoleAssembler authorProjectRoleAssembler) {
        assembler = authorProjectRoleAssembler;
    }

    @PostPersist
    public void onPostPersist(AuthorProjectRole role) {
        AuthorProjectRoleDto dto = assembler.toResource(role);
        Message<AuthorProjectRoleDto> message = new Message<>(dto, dto.getProjectId(), MessageType.PROJECT_ROLE_CREATION);
        lifecycleStreamer.sendProjectRoleCreationMessage(message);
    }

    @PostUpdate
    public void onPostUpdate(AuthorProjectRole role) {
        AuthorProjectRoleDto dto = assembler.toResource(role);
        Message<AuthorProjectRoleDto> message = new Message<>(dto, dto.getProjectId(), MessageType.PROJECT_ROLE_UPDATE);
        lifecycleStreamer.sendProjectRoleUpdateMessage(message);
    }
}
