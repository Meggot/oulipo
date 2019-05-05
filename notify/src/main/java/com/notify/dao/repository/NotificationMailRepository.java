package com.notify.dao.repository;

import com.notify.dao.entities.NotificationMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationMailRepository extends JpaRepository<NotificationMail, Integer>, QuerydslPredicateExecutor<NotificationMail> {
}
