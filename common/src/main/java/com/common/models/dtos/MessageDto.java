package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation("message")
public class MessageDto extends ResourceSupport implements Identifiable<Link> {

    private String value;

    private String fromUsername;

    private Integer fromUserId;

    private String toUsername;

    private Integer toUserId;

    private LocalDateTime sentAt;
}
