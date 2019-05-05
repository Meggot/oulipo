package com.common.models.dtos;

import lombok.Data;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
public class NotificationDto extends ResourceSupport implements Identifiable<Link> {

    public Integer idField;

    public Integer entityId;

    public NotificationType type;

    public String message;
}
