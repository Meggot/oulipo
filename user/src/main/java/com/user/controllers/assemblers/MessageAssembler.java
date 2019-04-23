package com.user.controllers.assemblers;

import com.common.models.dtos.MessageDto;
import com.user.controllers.MessageController;
import com.user.dao.entites.Message;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class MessageAssembler extends ResourceAssemblerSupport<Message, MessageDto> {

    public MessageAssembler() {
        super(MessageController.class, MessageDto.class);
    }

    @Override
    public MessageDto toResource(Message message) {
        MessageDto dto = createResourceWithId(message.getId(), message);
        dto.setFromUserId(message.getSender().getId());
        dto.setFromUsername(message.getSender().getUsername());
        dto.setToUsername(message.getRecipient().getUsername());
        dto.setToUserId(message.getRecipient().getId());
        dto.setSentAt(message.getCreationDate());
        dto.setValue(message.getValue());
        return dto;
    }
}
