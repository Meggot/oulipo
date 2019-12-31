// Copyright (c) 2019 Travelex Ltd

package com.metadata.services;

import com.common.models.enums.EntityType;
import com.common.models.dtos.MatrixDto;
import com.common.models.enums.MetricType;
import com.metadata.dao.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.common.models.enums.MetricType.*;

@Component
public class MatrixManagementService {

    @Autowired
    private MetricRepository metricRepository;

    public MatrixDto generateMatrixForTypeAndId(EntityType type, String entityId) {
        Integer id = Integer.parseInt(entityId);
        MatrixDto newMatrixDto = new MatrixDto();
        newMatrixDto.setEntityId(id);
        newMatrixDto.setEntityType(type);
        Map<MetricType, Integer> metricCounts = new HashMap<>();
        metricCounts.put(UPVOTE, metricRepository.countByEntityTypeAndEntityIdAndTypeEquals(type, id, UPVOTE));
        metricCounts.put(DOWNVOTE, metricRepository.countByEntityTypeAndEntityIdAndTypeEquals(type, id, DOWNVOTE));
        metricCounts.put(SHARE, metricRepository.countByEntityTypeAndEntityIdAndTypeEquals(type, id, SHARE));
        newMatrixDto.setMetricCounts(metricCounts);
        return newMatrixDto;
    }

}
