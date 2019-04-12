package com.user.dao.entites;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "Message")
@NoArgsConstructor
public class Message extends EntityObject {

    @Id
    @Column(name = "pk_message_id")
    @SequenceGenerator(name = "message_seq", sequenceName = "message_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="message_seq")
    private Integer id;

    @JoinColumn(name = "fk_sender_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Account sender;

    @JoinColumn(name = "fk_recipient_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Account recipient;

    @Column(name = "message_value")
    private String value;

}
