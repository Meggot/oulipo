// Copyright (c) 2019 Travelex Ltd

package com.notify.controllers.assemblers;

import com.common.models.dtos.NotificationDto;
import com.notify.controllers.NotificationController;
import com.notify.dao.entities.Notification;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class NotificationAssembler extends ResourceAssemblerSupport<Notification, NotificationDto> {



    public NotificationAssembler() {
        super(NotificationController.class, NotificationDto.class);
    }

    @Override
    public NotificationDto toResource(Notification notification) {
        NotificationDto dto = createResourceWithId(notification.getId(), notification);
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setEntityId(notification.getEntityId());
        dto.setIdField(notification.getId());
        dto.setBody(notification.getBody());
        return dto;
    }
}
