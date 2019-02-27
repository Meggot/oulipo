package com.user.dao.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Created by bradleyw on 25/03/2018.
 */
@Entity(name = "Password")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Password extends EntityObject{

    @Id
    @Column(name = "pk_pwd_id")
    @SequenceGenerator(name = "passwords_seq", sequenceName = "passwords_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="passwords_seq")
    private int id;

    @Column(name="hash_value")
    private String hashValue;


}
