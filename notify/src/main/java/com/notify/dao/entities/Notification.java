package com.notify.dao.entities;

import com.common.models.dtos.NotificationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Notification")
@Data
@NoArgsConstructor
public class Notification extends EntityObject {

    @Id
    @SequenceGenerator(name = "notifications_seq", sequenceName = "notifications_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_seq")
    @Column(name = "pk_notification_id")
    private Integer id;

    @Column(name = "entity_id")
    private Integer entityId;

    @Column(name = "type")
    private NotificationType type;

    @Column(name = "message")
    private String message;

    //Serialized into a JSON dto.
    @Column(name = "body")
    private String body;


}
