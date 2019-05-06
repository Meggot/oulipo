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
        notification.setMessage(message.getProjectTitle() + " has had a new tag of " + message.getValue());
        notification.setType(NotificationType.PROJECT_TAG_CREATED);
        return notificationRepository.save(notification);
    }

    public Notification toNotification(ProjectTagUpdateMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setMessage(message.getProjectTitle() + " has had a tag removed");
        notification.setType(NotificationType.PROJECT_TAG_REMOVED);
        return notificationRepository.save(notification);
    }

    public Notification toNotification(ProjectPartUpdateMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setMessage(message.getProjectTitle() + " has had a part " + message.getPartStatus());
        notification.setType(NotificationType.PROJECT_PART_UPDATED);
        return notificationRepository.save(notification);
    }


    public Notification toNotification(ProjectPartCreationMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setMessage(message.getProjectTitle() + " has had a part requested by " + message.getPartAuthorName());
        notification.setType(NotificationType.PROJECT_PART_POSTED);
        return notificationRepository.save(notification);
    }

    public Notification toNotification(MessageSentMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getToUserId());
        notification.setType(NotificationType.INBOX_MESSAGE_RECEIVED);
        notification.setMessage(message.getFromUsername() + ": " + message.getValue().substring( 0,
                Math.min(message.getValue().length(), 100)));
        return notificationRepository.save(notification);
    }

    public Notification toNotification(ProjectUpdateMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setType(NotificationType.PROJECT_PART_UPDATED);
        notification.setMessage(message.getNewTitle() + " has been updated.");
        return notificationRepository.save(notification);
    }

    public Notification toNotification(CopyEditUpdateMesage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setType(NotificationType.PROJECT_EDIT_UPDATED);
        notification.setMessage(message.getProjectTitle() + " has had an edit " + message.getEditStatus());
        return notificationRepository.save(notification);
    }

    public Notification toNotification(CopyEditCreationMessage message) {
        Notification notification = new Notification();
        notification.setEntityId(message.getProjectId());
        notification.setType(NotificationType.PROJECT_EDIT_POSTED);
        notification.setMessage((message.getProjectTitle() + " has had an edit requested by " + message.getAuthorName()));
        return notificationRepository.save(notification);
    }
}
