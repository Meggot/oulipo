// Copyright (c) 2019 Travelex Ltd

package com.project.dao.entites;

import lombok.Data;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "Author")
@Data
public class Author extends EntityObject{

    @Id
    @SequenceGenerator(name = "authors_seq", sequenceName = "authors_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authors_seq")
    @Column(name = "pk_author_id")
    private Integer authorId;

    @Column(name = "fk_user_id")
    private Integer userId;

    @Column(name = "username")
    private String username;

}
