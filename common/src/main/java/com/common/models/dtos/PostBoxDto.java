package com.common.models.dtos;

import lombok.Data;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostBoxDto extends ResourceSupport implements Identifiable<Link> {

    Integer idField;

    String address;

    PostFlagStatus flagStatus;

    Integer userId;

    LocalDateTime lastOpened;

    List<NotificationMailDto> unreadMail;

    List<SubscriptionDto> subscriptionDtos;

    boolean online;
}
