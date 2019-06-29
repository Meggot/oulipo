package com.notify.streaming;

import com.common.models.dtos.*;
import com.common.models.messages.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.notify.configuration.NotifySource;
import com.notify.services.NotificationManagementService;
import com.notify.services.NotificationsFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@Profile("!Test")
@EnableBinding(NotifySource.class)
public class NotifyStreamer implements AccountLifecycleStreamer, CopyLifecycleStreamer, ProjectLifecycleStreamer {

    @Autowired
    NotificationManagementService managementService;

    @Autowired
    NotificationsFactory factory;

    @Autowired
    private NotifySource userSource;

    @StreamListener(value = "message-sent")
    @Override
    public void handleAccountMessage(Message<MessageDto> message) {
        try {
            managementService.handleNotification(factory.handleMessageReceived(message));
        } catch (JsonProcessingException e) {
            log.error("Failure to serialize Notification body " + message + " into String", e);
        }
    }

    @StreamListener(value = "copy-edit-creation")
    @Override
    public void handleCopyEditCreation(Message<CopyEditDto> message) {
        try {
            managementService.handleNotification(factory.handleEditRequested(message));
        } catch (JsonProcessingException e) {
            log.error("Failure to serialize Notification body " + message + " into String", e);
        }
    }

    @StreamListener(value = "copy-edit-update")
    @Override
    public void handleCopyEditUpdate(Message<CopyEditDto> message) {
        try {
            managementService.handleNotification(factory.handleEditUpdated(message));
        } catch (JsonProcessingException e) {
            log.error("Failure to serialize Notification body " + message + " into String", e);
        }
    }

    @StreamListener(value = "project-tag-creation")
    @Override
    public void handleProjectTagCreation(Message<ProjectTagDto> message) {
        try {
            managementService.handleNotification(factory.handleTagCreated(message));
        } catch (JsonProcessingException e) {
            log.error("Failure to serialize Notification body " + message + " into String", e);
        }
    }

    @StreamListener(value = "project-tag-update")
    @Override
    public void handleProjectTagUpdate(Message<ProjectTagDto> message) {
        try {
            managementService.handleNotification(factory.handleTagRemoved(message));
        } catch (JsonProcessingException e) {
            log.error("Failure to serialize Notification body " + message + " into String", e);
        }
    }

    @StreamListener(value = "copy-part-update")
    @Override
    public void handleProjectPartUpdate(Message<ProjectPartDto> message) {
        try {
            managementService.handleNotification(factory.handlePartUpdated(message));
        } catch (JsonProcessingException e) {
            log.error("Failure to serialize Notification body " + message + " into String", e);
        }
    }

    @StreamListener(value = "copy-part-creation")
    @Override
    public void handleProjectPartCreation(Message<ProjectPartDto> message) {
        try {
            managementService.handleNotification(factory.handlePartRequested(message));
        } catch (JsonProcessingException e) {
            log.error("Failure to serialize Notification body " + message + " into String", e);
        }
    }

    @StreamListener(value = "project-update")
    @Override
    public void handleProjectUpdate(Message<ProjectDto> message) {
        try {
            managementService.handleNotification(factory.handleProjectUpdated(message));
        } catch (JsonProcessingException e) {
            log.error("Failure to serialize Notification body " + message + " into String", e);
        }
    }
}
