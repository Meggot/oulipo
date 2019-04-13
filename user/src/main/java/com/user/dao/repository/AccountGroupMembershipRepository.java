package com.user.dao.repository;

import com.user.dao.entites.AccountGroupMembership;
import com.user.dao.entites.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountGroupMembershipRepository extends JpaRepository<AccountGroupMembership, Integer>, QuerydslPredicateExecutor<AccountGroupMembership> {
}
