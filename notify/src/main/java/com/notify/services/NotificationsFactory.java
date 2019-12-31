package com.notify.services;

import com.common.models.dtos.*;
import com.common.models.enums.NotificationType;
import com.common.models.messages.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notify.dao.entities.Notification;
import com.notify.dao.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationsFactory {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ObjectMapper objectMapper;


    public Notification handleTagCreated(Message<ProjectTagDto> tagCreated) throws JsonProcessingException {
        Notification notification = new Notification();
        notification.setEntityId(tagCreated.getEntityId());
        notification.setMessage(tagCreated.getBody().getProjectTitle()+ " has had a new tag of " + tagCreated.getBody().getProjectTitle());
        notification.setType(NotificationType.PROJECT_TAG_CREATED);
        notification.setBody(objectMapper.writeValueAsString(tagCreated.getBody()));
        return notificationRepository.save(notification);
    }

    public Notification handleTagRemoved(Message<ProjectTagDto> tagRemoved) throws JsonProcessingException {
        Notification notification = new Notification();
        notification.setEntityId(tagRemoved.getEntityId());
        notification.setMessage(tagRemoved.getBody().getProjectTitle() + " has had a tag removed");
        notification.setType(NotificationType.PROJECT_TAG_REMOVED);
        notification.setBody(objectMapper.writeValueAsString(tagRemoved.getBody()));
        return notificationRepository.save(notification);
    }

    public Notification handlePartUpdated(Message<ProjectPartDto> partUpdated) throws JsonProcessingException {
        Notification notification = new Notification();
        notification.setEntityId(partUpdated.getEntityId());
        notification.setMessage(partUpdated.getBody().getProjectTitle() + " has had a part "
                + partUpdated.getBody().getStatus());
        notification.setType(NotificationType.PROJECT_PART_UPDATED);
        notification.setBody(objectMapper.writeValueAsString(partUpdated.getBody()));
        return notificationRepository.save(notification);
    }


    public Notification handlePartRequested(Message<ProjectPartDto> partRequested) throws JsonProcessingException {
        Notification notification = new Notification();
        notification.setEntityId(partRequested.getEntityId());
        notification.setMessage(partRequested.getBody().getProjectTitle() + " has had a part requested by "
                + partRequested.getBody().getAuthorName());
        notification.setType(NotificationType.PROJECT_PART_POSTED);
        notification.setBody(objectMapper.writeValueAsString(partRequested.getBody()));
        return notificationRepository.save(notification);
    }

    public Notification handleMessageReceived(Message<MessageDto> messageReceived) throws JsonProcessingException {
        Notification notification = new Notification();
        notification.setEntityId(messageReceived.getEntityId());
        notification.setType(NotificationType.INBOX_MESSAGE_RECEIVED);
        notification.setMessage(messageReceived.getBody().getValue().substring(0, Math.min(messageReceived.getBody().getValue().length(), 100)));
        notification.setBody(objectMapper.writeValueAsString(messageReceived.getBody()));
        return notificationRepository.save(notification);
    }

    public Notification handleProjectUpdated(Message<ProjectDto> projectUpdated) throws JsonProcessingException {
        Notification notification = new Notification();
        notification.setEntityId(projectUpdated.getEntityId());
        notification.setType(NotificationType.PROJECT_PART_UPDATED);
        notification.setMessage(projectUpdated.getBody().getTitle() + " has been updated.");
        notification.setBody(objectMapper.writeValueAsString(projectUpdated.getBody()));
        return notificationRepository.save(notification);
    }

    public Notification handleEditUpdated(Message<CopyEditDto> projectEditMessage) throws JsonProcessingException {
        Notification notification = new Notification();
        notification.setEntityId(projectEditMessage.getEntityId());
        notification.setType(NotificationType.PROJECT_EDIT_UPDATED);
        notification.setMessage(projectEditMessage.getBody().getProjectTitle() + " has had an edit "
                + projectEditMessage.getBody().getStatus());
        notification.setBody(objectMapper.writeValueAsString(projectEditMessage.getBody()));
        return notificationRepository.save(notification);
    }

    public Notification handleEditRequested(Message<CopyEditDto> projectEditPosted) throws JsonProcessingException {
        Notification notification = new Notification();
        notification.setEntityId(projectEditPosted.getEntityId());
        notification.setType(NotificationType.PROJECT_EDIT_POSTED);
        notification.setMessage((projectEditPosted.getBody().getProjectTitle() + " has had an edit requested by "
                + projectEditPosted.getBody().getAuthorName()));
        notification.setBody(objectMapper.writeValueAsString(projectEditPosted.getBody()));
        return notificationRepository.save(notification);
    }
}
