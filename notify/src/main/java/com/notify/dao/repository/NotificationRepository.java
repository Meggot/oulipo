package com.notify.dao.repository;

import com.notify.dao.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer>, QuerydslPredicateExecutor<Notification> {
}
