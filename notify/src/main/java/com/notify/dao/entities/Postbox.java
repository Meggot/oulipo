package com.notify.dao.entities;

import com.common.models.enums.PostFlagStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Postbox")
@Data
@NoArgsConstructor
public class Postbox extends EntityObject {

    @Id
    @SequenceGenerator(name = "pk_postbox_id", sequenceName = "pk_postbox_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_postbox_id")
    @Column(name = "pk_postbox_id")
    private Integer id;

    @Column(name = "address")
    private String address;

    @Column(name = "online")
    private boolean online;

    @Column(name = "mail_flag_status")
    private PostFlagStatus postFlagStatus;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "last_accessed")
    private LocalDateTime lastOpened;

    @OneToMany(mappedBy = "postbox", cascade = CascadeType.ALL)
    private List<Subscription> subscriptionList = new ArrayList<>();

    @OneToMany(mappedBy = "postbox", cascade = CascadeType.ALL)
    private List<NotificationMail> mail = new ArrayList<>();

    public void addMail(NotificationMail mail) {
        this.mail.add(mail);
        mail.setPostbox(this);
    }

    public void addSubscription(Subscription subscription) {
        this.subscriptionList.add(subscription);
        subscription.setPostbox(this);
    }

}
