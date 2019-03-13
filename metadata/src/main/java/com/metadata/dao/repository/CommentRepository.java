// Copyright (c) 2019 Travelex Ltd

package com.metadata.dao.repository;

import com.metadata.dao.entites.Comment;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Integer>, QuerydslPredicateExecutor {
}
