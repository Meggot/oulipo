package com.user.dao.entites;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "AccountLogin")
@Data
@NoArgsConstructor
public class AccountLogin extends EntityObject {

    @Id
    @SequenceGenerator(name = "account_login_seq", sequenceName = "account_login_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_login_seq")
    @Column(name = "pk_account_login_id")
    private int id;

    @JoinColumn(name = "fk_account_id")
    @ManyToOne
    private Account account;

    @Column(name = "ip_address")
    private String ipAddress;
}