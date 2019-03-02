package com.project.dao.repository;

import com.project.dao.entites.ProjectPart;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends PagingAndSortingRepository<ProjectPart,Integer>, QuerydslPredicateExecutor<ProjectPart> {
}
