// Copyright (c) 2019 Travelex Ltd

package com.audit.dao.entites;

import com.common.models.enums.EntityType;
import com.common.models.messages.MessageType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Audits")
public class Audit extends EntityObject {

    @Id
    @SequenceGenerator(name = "audits_seq", sequenceName = "audits_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audits_seq")
    @Column(name = "pk_project_id")
    private Integer id;

    @Column(name = "entity_id")
    private Integer entityId;

    @Column(name = "entity_type")
    private EntityType entityType;

    @Column(name = "event_type")
    private MessageType eventType;

    @Column(name = "origin_user_id")
    private Integer originUserId;

    @Column(name = "value")
    private String value;
}
