package com.user.dao.repository;

import com.user.dao.entites.AccountLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLoginRepository extends JpaRepository<AccountLogin, Integer>, QuerydslPredicateExecutor<AccountLogin> {
}
