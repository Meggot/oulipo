package com.common.models.dtos;

import com.common.models.enums.NotificationType;
import lombok.Data;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Data
@Relation("notification")
public class NotificationDto extends ResourceSupport implements Identifiable<Link> {

    public Integer idField;

    public Integer entityId;

    public NotificationType type;

    public String message;

    public String body;
}
