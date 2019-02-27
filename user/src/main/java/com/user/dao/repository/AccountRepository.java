package com.user.dao.repository;

import com.user.dao.entites.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, QuerydslPredicateExecutor<Account> {
    Account getAccountByEmail(String email);
    Optional<Account> getAccountByUsername(String username);
    boolean existsAccountByUsername(String username);
    boolean existsAccountByEmail(String email);
}
