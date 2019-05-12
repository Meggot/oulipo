package com.project.dao.handlers;

import com.common.models.dtos.CopyEditDto;
import com.common.models.messages.Message;
import com.common.models.messages.MessageType;
import com.project.controllers.assemblers.CopyEditAssembler;
import com.project.controllers.assemblers.PartAssembler;
import com.project.dao.entites.CopyEdit;
import com.project.streaming.ProjectLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

@Slf4j
@Component
public class EditEventHandler {

    public static ProjectLifecycleStreamer lifecycleStreamer;

    public static CopyEditAssembler editAssembler;

    @Autowired
    public void setLifecycleStreamer(ProjectLifecycleStreamer projectLifecycleStreamer) {
        this.lifecycleStreamer = projectLifecycleStreamer;
    }

    @Autowired
    public void setPartAssembler(CopyEditAssembler editAssembler) {
        this.editAssembler = editAssembler;
    }


    @PostPersist
    public void onPostPersist(CopyEdit edit) {
        CopyEditDto dto = editAssembler.toResource(edit);
        Message<CopyEditDto> message = new Message<>(dto, dto.getProjectId(), MessageType.COPY_EDIT_CREATION);
        lifecycleStreamer.sendCopyEditCreationMessage(message);
    }

    @PostUpdate
    public void onPostUpdate(CopyEdit edit) {
        CopyEditDto dto = editAssembler.toResource(edit);
        Message<CopyEditDto> message = new Message<>(dto, dto.getProjectId(), MessageType.COPY_EDIT_UPDATE);
        lifecycleStreamer.sendCopyEditUpdateMessage(message);
    }

}
