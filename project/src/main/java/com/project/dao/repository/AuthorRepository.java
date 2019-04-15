// Copyright (c) 2019 Travelex Ltd

package com.project.dao.repository;

import com.project.dao.entites.Author;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer>, QuerydslPredicateExecutor<Author> {
    boolean existsByUserIdEquals(Integer userId);
    Optional<Author> findAuthorByUserIdEquals(Integer userId);
    Optional<Author> findAuthorByUsernameEquals(String username);
}
