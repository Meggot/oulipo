package com.common.models.dtos;

import com.common.models.enums.NotificationType;
import lombok.Data;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Data
@Relation("subscription")
public class SubscriptionDto extends ResourceSupport implements Identifiable<Link> {

    Integer idField;

    NotificationType notificationType;

    Integer entityId;

    Integer postboxId;
}
