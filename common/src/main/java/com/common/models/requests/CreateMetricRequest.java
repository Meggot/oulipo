package com.common.models.requests;

import com.common.models.enums.EntityType;
import com.common.models.enums.MetricType;
import lombok.Data;

import javax.validation.Valid;

@Data
@Valid
public class CreateMetricRequest {
    private MetricType type;
    private Integer entityId;
    private EntityType entityType;
}
