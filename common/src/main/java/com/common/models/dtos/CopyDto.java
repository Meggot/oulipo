package com.common.models.dtos;

import lombok.Data;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
public class CopyDto extends ResourceSupport implements Identifiable<Link> {
    Integer idField;

    Integer projectId;

    String value;
}
