// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import com.common.models.enums.EntityType;
import com.common.models.enums.MetricType;
import lombok.Data;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Map;

@Data
@Relation("matrix")
public class MatrixDto extends ResourceSupport implements Identifiable<Link> {
    private EntityType entityType;
    private Integer entityId;
    private Map<MetricType, Integer> metricCounts;
}
