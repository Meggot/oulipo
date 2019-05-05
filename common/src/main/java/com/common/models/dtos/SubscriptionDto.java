package com.common.models.dtos;

import lombok.Data;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
public class SubscriptionDto extends ResourceSupport implements Identifiable<Link> {

    Integer idField;

    NotificationType notificationType;

    Integer entityId;

    Integer postboxId;
}
