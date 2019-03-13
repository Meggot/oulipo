package com.project.dao.repository;

import com.project.dao.entites.ProjectTag;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTagRepository extends PagingAndSortingRepository<ProjectTag, Integer>, QuerydslPredicateExecutor<ProjectTag> {
}
