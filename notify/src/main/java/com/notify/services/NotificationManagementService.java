package com.notify.services;

import com.notify.dao.entities.Notification;
import com.notify.dao.entities.Subscription;
import com.notify.dao.repository.NotificationRepository;
import com.notify.dao.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class NotificationManagementService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    PostboxManagementService postboxManagementService;

    @Transactional
    public void handleNotification(Notification notification) {
        List<Subscription> subscriptionList = subscriptionRepository.findAllByNotificationTypeEqualsAndEntityIdEquals(notification.getType(), notification.getEntityId());
        subscriptionList.forEach((subscription -> {
            postboxManagementService.processSubscriptionNotification(subscription.getPostbox(), notification);
        }));
    }
}
