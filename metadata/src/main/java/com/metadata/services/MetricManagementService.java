// Copyright (c) 2019 Travelex Ltd

package com.metadata.services;

import com.common.models.dtos.MetricDto;
import com.common.models.responses.EntityModificationResponse;
import com.metadata.dao.entites.Metric;
import com.metadata.dao.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricManagementService {

    @Autowired
    MetricRepository metricRepository;

    public EntityModificationResponse<Metric> createMetric(MetricDto metricDto) {
        EntityModificationResponse<Metric> response = new EntityModificationResponse<>();
        Metric newMetric = new Metric();
        newMetric.setEntityId(metricDto.getEntityId());
        newMetric.setEntityType(metricDto.getEntityType());
        newMetric.setType(metricDto.getType());
        newMetric.setUserId(metricDto.getUserId());
        response.setEntity(metricRepository.save(newMetric));
        return response;
    }

}
