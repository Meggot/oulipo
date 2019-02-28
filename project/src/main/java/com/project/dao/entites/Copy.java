// Copyright (c) 2019 Travelex Ltd

package com.project.dao.entites;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;

@Entity(name = "Copy")
public class Copy extends EntityObject{

    @Id
    @SequenceGenerator(name = "copy_seq", sequenceName = "copy_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "copy_seq")
    @Column(name = "pk_copy_id")
    private Integer id;

    @JoinColumn(name = "project_id")
    private Project projectId;

    @Column(name = "value")
    private String value;
}
