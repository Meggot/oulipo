package com.notify.streaming;

import com.common.models.messages.CopyEditCreationMessage;
import com.common.models.messages.CopyEditUpdateMesage;
import com.notify.services.NotificationManagementService;
import com.notify.services.NotificationsFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("Test")
public class CopyInMemoryLifecycleStreamer implements CopyLifecycleStreamer {


    @Autowired
    NotificationManagementService managementService;

    @Autowired
    NotificationsFactory factory;


    @Override
    public void listen(CopyEditUpdateMesage message, int partition) {
        log.info(">[INMEMORY] Received a message in the partition [{}] contents: [{}]", partition, message);
        managementService.handleNotification(factory.toNotification(message));
    }

    @Override
    public void listen(CopyEditCreationMessage message, int partition) {
        log.info(">[INMEMORY] Received a message in the partition [{}] contents: [{}]", partition, message);
        managementService.handleNotification(factory.toNotification(message));
    }
}
