// Copyright (c) 2019 Travelex Ltd

package com.metadata.services;

import com.common.models.requests.CreateMetricRequest;
import com.metadata.dao.entites.Metric;
import com.metadata.dao.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricManagementService {

    @Autowired
    MetricRepository metricRepository;

    public Metric createMetric(CreateMetricRequest metricDto, String userId) {
        Metric newMetric = new Metric();
        newMetric.setEntityId(metricDto.getEntityId());
        newMetric.setEntityType(metricDto.getEntityType());
        newMetric.setType(metricDto.getType());
        newMetric.setUserId(Integer.parseInt(userId));
        return metricRepository.save(newMetric);
    }

}
