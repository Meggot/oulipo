package com.notify.services;

import com.common.models.dtos.NotificationType;
import com.common.models.messages.*;
import com.notify.dao.entities.Notification;
import com.notify.dao.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationsFactory {

    @Autowired
    NotificationRepository notificationRepository;

    public Notification toNotification(ProjectTagCreationMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setMessage("Tagged!");
        notification.setType(NotificationType.PROJECT_TAG_CREATED);
        return notificationRepository.save(notification);
    }

    public Notification toNotification(ProjectTagUpdateMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setMessage("Untagged!");
        notification.setType(NotificationType.PROJECT_TAG_REMOVED);
        return notificationRepository.save(notification);
    }

    public Notification toNotification(ProjectPartUpdateMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setMessage("A part has been " + message.getPartStatus());
        notification.setType(NotificationType.PROJECT_PART_UPDATED);
        return notificationRepository.save(notification);
    }


    public Notification toNotification(ProjectPartCreationMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setMessage("A part has been requested");
        notification.setType(NotificationType.PROJECT_PART_POSTED);
        return notificationRepository.save(notification);
    }

    public Notification toNotification(MessageSentMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getToUserId());
        notification.setType(NotificationType.INBOX_MESSAGE_RECEIVED);
        notification.setMessage("You've got mail from " + message.getFromUsername());
        return notificationRepository.save(notification);
    }

    public Notification toNotification(ProjectUpdateMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setType(NotificationType.PROJECT_PART_UPDATED);
        notification.setMessage("Project updated");
        return notificationRepository.save(notification);
    }

    public Notification toNotification(CopyEditUpdateMesage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setType(NotificationType.PROJECT_EDIT_UPDATED);
        notification.setMessage("Edit has just been " + message.getEditStatus());
        return notificationRepository.save(notification);
    }

    public Notification toNotification(CopyEditCreationMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setType(NotificationType.PROJECT_EDIT_POSTED);
        notification.setMessage(("An Edit has been submitted"));
        return notificationRepository.save(notification);
    }
}
