package com.notify.controllers.assemblers;

import com.common.models.enums.NotificationStatus;
import com.common.models.dtos.PostBoxDto;
import com.notify.controllers.PostboxController;
import com.notify.dao.entities.Postbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostboxAssembler extends ResourceAssemblerSupport<Postbox, PostBoxDto> {

    @Autowired
    public NotificationMailAssembler notificationMailAssembler;

    @Autowired
    public SubscriptionAssembler subscriptionAssembler;

    public PostboxAssembler() {

        super(PostboxController.class, PostBoxDto.class);
    }

    @Override
    public PostBoxDto toResource(Postbox postbox) {
        PostBoxDto postBoxDto = createResourceWithId(postbox.getId(), postbox);
        postBoxDto.setAddress(postbox.getAddress());
        postBoxDto.setFlagStatus(postbox.getPostFlagStatus());
        postBoxDto.setIdField(postbox.getId());
        postBoxDto.setUserId(postbox.getUserId());
        postBoxDto.setLastOpened(postbox.getLastOpened());
        postBoxDto.setOnline(postbox.isOnline());
        postBoxDto.setUnreadMail(postbox.getMail().stream()
                .filter(mail -> mail.getStatus() == NotificationStatus.UNREAD)
                .map(notificationMailAssembler::toResource)
                .collect(Collectors.toList()));
        postBoxDto.setSubscriptionDtos(postbox.getSubscriptionList().stream()
                .map(subscriptionAssembler::toResource)
                .collect(Collectors.toList()));
        return postBoxDto;
    }
}
