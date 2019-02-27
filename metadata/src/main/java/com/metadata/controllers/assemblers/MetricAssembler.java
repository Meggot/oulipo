// Copyright (c) 2019 Travelex Ltd

package com.metadata.controllers.assemblers;

import com.common.models.dtos.MetricDto;
import com.metadata.controllers.MetricController;
import com.metadata.dao.entites.Metric;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class MetricAssembler extends ResourceAssemblerSupport<Metric, MetricDto> {

    public MetricAssembler() {
        super(MetricController.class, MetricDto.class);
    }

    @Override
    public MetricDto toResource(Metric metric) {
        MetricDto dto = createResourceWithId(metric.getId(), metric);
        dto.setEntityId(metric.getEntityId());
        dto.setEntityType(metric.getEntityType());
        dto.setIdField(metric.getId());
        dto.setType(metric.getType());
        dto.setUserId(metric.getUserId());
        return dto;
    }
}
