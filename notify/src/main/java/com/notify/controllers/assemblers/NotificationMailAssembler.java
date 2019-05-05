package com.notify.controllers.assemblers;

import com.common.models.dtos.NotificationMailDto;
import com.notify.controllers.NotificationMailController;
import com.notify.dao.entities.NotificationMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class NotificationMailAssembler extends ResourceAssemblerSupport<NotificationMail, NotificationMailDto> {

    @Autowired
    NotificationAssembler notificationAssembler;

    public NotificationMailAssembler() {
        super(NotificationMailController.class, NotificationMailDto.class);
    }

    @Override
    public NotificationMailDto toResource(NotificationMail notificationMail) {
        NotificationMailDto dto = createResourceWithId(notificationMail.getId(), notificationMail);
        dto.setIdField(notificationMail.getId());
        dto.setNotification(notificationAssembler.toResource(notificationMail.getNotification()));
        dto.setStatus(notificationMail.getStatus());
        return dto;
    }
}
