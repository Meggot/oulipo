package com.project.dao.repository;

import com.project.dao.entites.AuthorProjectRole;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorProjectRoleRepository extends PagingAndSortingRepository<AuthorProjectRole, Integer>, QuerydslPredicateExecutor<AuthorProjectRole> {
}
