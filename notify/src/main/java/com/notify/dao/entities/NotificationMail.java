package com.notify.dao.entities;

import com.common.models.dtos.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "NotificationMail")
public class NotificationMail extends EntityObject{

    @Id
    @SequenceGenerator(name = "notifications_mail_seq", sequenceName = "notifications_mail_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notifications_mail_seq")
    @Column(name = "pk_notification_mail_id")
    private Integer id;

    @Column
    private NotificationStatus status;

    @ManyToOne
    @JoinColumn(name = "fk_notification_id")
    private Notification notification;

    @ManyToOne
    @JoinColumn(name = "fk_postbox_id")
    private Postbox postbox;
}
