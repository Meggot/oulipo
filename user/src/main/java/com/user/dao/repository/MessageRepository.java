package com.user.dao.repository;

import com.user.dao.entites.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>, QuerydslPredicateExecutor<Message> {
}
