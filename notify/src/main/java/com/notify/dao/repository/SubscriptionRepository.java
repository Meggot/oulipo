package com.notify.dao.repository;

import com.common.models.enums.NotificationType;
import com.notify.dao.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>, QuerydslPredicateExecutor<Subscription> {
    List<Subscription> findAllByNotificationTypeEqualsAndEntityIdEquals(NotificationType type, Integer entityId);
}
