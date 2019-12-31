package com.common.models.dtos;

import com.common.models.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMailDto extends ResourceSupport implements Identifiable<Link> {

    Integer idField;

    NotificationDto notification;

    LocalDateTime received;

    NotificationStatus status;

}
