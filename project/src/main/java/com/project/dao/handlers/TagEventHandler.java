package com.project.dao.handlers;

import com.common.models.dtos.ProjectTagDto;
import com.common.models.messages.Message;
import com.common.models.messages.MessageType;
import com.project.controllers.assemblers.ProjectTagAssembler;
import com.project.dao.entites.ProjectTag;
import com.project.streaming.ProjectLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

@Slf4j
@Component
public class TagEventHandler {

    public static ProjectLifecycleStreamer lifecycleStreamer;

    public static ProjectTagAssembler tagAssembler;

    @Autowired
    public void setLifecycleStreamer(ProjectLifecycleStreamer projectLifecycleStreamer) {
        lifecycleStreamer = projectLifecycleStreamer;
    }

    @Autowired
    public void setTagAssembler(ProjectTagAssembler tagAssembler) {
        TagEventHandler.tagAssembler = tagAssembler;
    }

    @PostPersist
    public void postPersist(ProjectTag tag) {
        ProjectTagDto dto = tagAssembler.toResource(tag);
        Message<ProjectTagDto> message = new Message<>(dto, tag.getProject().getId(), MessageType.PROJECT_TAG_CREATION);
        lifecycleStreamer.sendProjectTagCreationMessage(message);
    }


    @PostRemove
    public void postRemove(ProjectTag tag) {
        ProjectTagDto dto = tagAssembler.toResource(tag);
        Message<ProjectTagDto> message = new Message<>(dto, tag.getProject().getId(), MessageType.PROJECT_TAG_DELETION);
        lifecycleStreamer.sendProjectTagUpdateMessage(message);
    }
}