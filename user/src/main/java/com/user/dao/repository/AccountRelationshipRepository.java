package com.user.dao.repository;

import com.user.dao.entites.AccountRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRelationshipRepository extends JpaRepository<AccountRelationship, Integer>, QuerydslPredicateExecutor<AccountRelationship> {

}
