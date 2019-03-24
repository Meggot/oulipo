package com.common.models.requests;

import com.common.models.dtos.EntityType;
import com.common.models.dtos.MetricType;
import lombok.Data;

import javax.validation.Valid;

@Data
@Valid
public class CreateMetricRequest {
    private MetricType type;
    private Integer entityId;
    private EntityType entityType;
}
