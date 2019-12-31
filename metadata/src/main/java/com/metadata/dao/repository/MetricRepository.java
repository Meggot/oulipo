// Copyright (c) 2019 Travelex Ltd

package com.metadata.dao.repository;

import com.common.models.enums.EntityType;
import com.common.models.enums.MetricType;
import com.metadata.dao.entites.Metric;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends PagingAndSortingRepository<Metric, Integer>, QuerydslPredicateExecutor {
    Integer countByEntityTypeAndEntityIdAndTypeEquals(EntityType entityType, Integer entityId, MetricType type);
}
