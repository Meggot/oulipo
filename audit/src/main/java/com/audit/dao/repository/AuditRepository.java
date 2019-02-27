// Copyright (c) 2019 Travelex Ltd

package com.audit.dao.repository;

import com.audit.dao.entites.Audit;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuditRepository extends PagingAndSortingRepository<Audit, Integer>, QuerydslPredicateExecutor {

}
