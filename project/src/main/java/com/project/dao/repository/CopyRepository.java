// Copyright (c) 2019 Travelex Ltd

package com.project.dao.repository;

import com.project.dao.entites.Copy;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyRepository extends PagingAndSortingRepository<Copy, Integer>, QuerydslPredicateExecutor<Copy> {

}
