// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import com.common.models.messages.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AuditDto extends ResourceSupport implements Identifiable<Link> {

    private Integer idField;

    private Integer entityId;

    private MessageType eventType;

    private Integer originUserId;

    private String value;

    private LocalDateTime timeStamp;
}
