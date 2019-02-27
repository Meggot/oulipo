package com.user.dao.entites;

import com.common.models.dtos.AccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "Account")
@Data
@NoArgsConstructor
public class Account extends EntityObject {

    @Id
    @SequenceGenerator(name = "accounts_seq", sequenceName = "accounts_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_seq")
    @Column(name = "pk_user_id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_pwd_id")
    private Password password;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(name = "status")
    private AccountStatus status = AccountStatus.ACTIVE;

    @PersistenceConstructor
    public Account(final String accountUsername, final String accountEmail, final Password accountPassword) {
        super();
        this.username = accountUsername;
        this.email = accountEmail;
        this.password = accountPassword;
    }

}
