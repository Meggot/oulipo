// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import lombok.Data;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Map;

@Data
public class MatrixDto extends ResourceSupport implements Identifiable<Link> {
    private EntityType entityType;
    private Integer entityId;
    private Map<MetricType, Integer> metricCounts;
}
