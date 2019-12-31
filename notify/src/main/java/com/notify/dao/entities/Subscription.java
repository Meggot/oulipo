package com.notify.dao.entities;

import com.common.models.enums.NotificationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Subscription")
@Data
@NoArgsConstructor
public class Subscription extends EntityObject{

    @Id
    @SequenceGenerator(name = "subscriptions_seq", sequenceName = "subscriptions_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscriptions_seq")
    @Column(name = "pk_subscription_id")
    private Integer id;

    @Column(name = "notification_type")
    private NotificationType notificationType;

    @Column(name = "entity_id")
    private Integer entityId;

    @ManyToOne
    @JoinColumn(name = "fk_postbox_id")
    private Postbox postbox;


}
