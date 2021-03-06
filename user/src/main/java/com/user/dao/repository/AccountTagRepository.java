package com.user.dao.repository;

import com.user.dao.entites.AccountTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTagRepository extends JpaRepository<AccountTag, Integer>, QuerydslPredicateExecutor<AccountTag> {
}
