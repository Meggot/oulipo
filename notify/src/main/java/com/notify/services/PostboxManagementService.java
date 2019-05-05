package com.notify.services;

import com.common.models.dtos.NotificationStatus;
import com.common.models.dtos.PostFlagStatus;
import com.common.models.requests.SubscribeNotificationRequest;
import com.notify.dao.entities.Notification;
import com.notify.dao.entities.NotificationMail;
import com.notify.dao.entities.Postbox;
import com.notify.dao.entities.Subscription;
import com.notify.dao.repository.NotificationMailRepository;
import com.notify.dao.repository.PostboxRepository;
import com.notify.dao.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Component
@Slf4j
public class PostboxManagementService {

    @Autowired
    PostboxRepository postboxRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    NotificationMailRepository notificationMailRepository;

    public Postbox handleGetMyPost(Integer userid) {

        Postbox postbox = retrievePostbox(userid);

        return postbox;
    }


    private Postbox retrievePostbox(Integer userid) {
        Optional<Postbox> userAlreadyHasOptional = postboxRepository
                .findByUserIdEquals(userid);
        if (userAlreadyHasOptional.isPresent()) {
            return userAlreadyHasOptional.get();
        }

        Postbox newPostbox = new Postbox();
        newPostbox.setAddress("REST");
        newPostbox.setUserId(userid);
        this.readMail(newPostbox);

        log.info("User {} did not have a Postbox, generating one..", userid);
        return postboxRepository.save(newPostbox);
    }


    public List<Subscription> handleSubscribe(Postbox postbox, SubscribeNotificationRequest request, String userId) {
        log.info(">[SUBSCRIBE] {} has sent a subscription request to {}. Request: {}",
                userId, postbox.getId(), request);
        List<Subscription> subscriptions = request.getTypes()
                .stream()
                .map(type -> {
                    Subscription subscription = new Subscription();
                    subscription.setEntityId(request.getEntityId());
                    subscription.setNotificationType(type);
                    subscription.setPostbox(postbox);
                    postbox.addSubscription(subscription);
                    return subscriptionRepository.save(subscription);
                })
                .collect(Collectors.toList());
        return subscriptions;
    }

    public void processSubscriptionNotification(Postbox postbox, Notification notification) {
        if (postbox.isOnline()) {
            NotificationMail notificationMail = new NotificationMail();
            notificationMail.setStatus(NotificationStatus.UNREAD);
            notificationMail.setNotification(notification);
            notificationMail = notificationMailRepository.save(notificationMail);
            postbox.addMail(notificationMail);
            postbox.setPostFlagStatus(PostFlagStatus.UNREAD);
            postboxRepository.save(postbox);
            log.info(">[SUBSCRIBE] {} Online to get their subscription!", postbox.getUserId());
        } else {
            log.info(">[SUBSCRIBE] {} Not online to get their subscription", postbox.getUserId());
        }
    }

    public void readMail(Postbox postBox) {
        postBox.setLastOpened(now());
        postBox.setOnline(true);
        postBox.setPostFlagStatus(PostFlagStatus.READ);
        postBox.getMail()
                .forEach(mail -> mail.setStatus(NotificationStatus.READ));
        postboxRepository.save(postBox);
    }
}
