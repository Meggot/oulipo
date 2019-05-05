package com.notify.dao.repository;

import com.notify.dao.entities.Postbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostboxRepository extends JpaRepository<Postbox, Integer>, QuerydslPredicateExecutor<Postbox> {
     Optional<Postbox> findByUserIdEquals(Integer userId);
}
