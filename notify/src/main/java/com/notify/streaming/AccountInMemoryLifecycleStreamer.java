package com.notify.streaming;

import com.common.models.messages.MessageSentMessage;
import com.notify.services.NotificationManagementService;
import com.notify.services.NotificationsFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("Test")
public class AccountInMemoryLifecycleStreamer implements AccountLifecycleStreamer {


    @Autowired
    NotificationManagementService managementService;

    @Autowired
    NotificationsFactory factory;

    @Override
    public void listen(MessageSentMessage message, int partition) {
        log.info(">[INMEMORY] Received a message in the partition [{}] contents: [{}]", partition, message);
        managementService.handleNotification(factory.toNotification(message));
    }
}
