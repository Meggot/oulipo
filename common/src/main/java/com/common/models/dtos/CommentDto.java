package com.common.models.dtos;

import com.common.models.enums.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto extends ResourceSupport implements Identifiable<Link> {
    private Integer idField;
    private Integer entityId;
    private Integer userId;
    private EntityType entityType;
    private String value;
    private String creationDate;
}
