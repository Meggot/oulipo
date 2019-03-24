package com.project.dao.repository;

import com.project.dao.entites.CopyEdit;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyEditRepository extends PagingAndSortingRepository<CopyEdit, Integer>, QuerydslPredicateExecutor<CopyEdit> {
}
