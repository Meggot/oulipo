// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import lombok.Data;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
public class MetricDto extends ResourceSupport implements Identifiable<Link> {

    Integer idField;
    MetricType type;
    Integer entityId;
    EntityType entityType;
    Integer userId;
}
