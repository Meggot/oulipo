package com.user.dao.entites;

import com.common.models.dtos.AccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.*;
import javax.persistence.*;

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

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountLogin> logins = new ArrayList<>();

    @OneToMany(mappedBy = "addedBy")
    private List<AccountRelationship> relationshipsAddedBy;

    @OneToMany(mappedBy = "added")
    private List<AccountRelationship> relationshipsAdded;

    @OneToMany(mappedBy = "account")
    private List<AccountTag> tags;

    public List<AccountRelationship> getRelationships() {
        ArrayList<AccountRelationship> accountRelationships = new ArrayList<>();
        accountRelationships.addAll(relationshipsAdded);
        accountRelationships.addAll(relationshipsAddedBy);
        return accountRelationships;
    }

    @PersistenceConstructor
    public Account(final String accountUsername, final String accountEmail, final Password accountPassword) {
        super();
        this.username = accountUsername;
        this.email = accountEmail;
        this.password = accountPassword;
    }

    public void addTag(AccountTag accountTag) {
        tags.add(accountTag);
        accountTag.setAccount(this);
    }

    public void addAccountLogin(AccountLogin accountLogin) {
        logins.add(accountLogin);
        accountLogin.setAccount(this);
    }

    public void addRelationshipAddedBy(AccountRelationship accountRelationship) {
        this.relationshipsAddedBy.add(accountRelationship);
        accountRelationship.setAddedBy(this);
    }

    public void addRelationshipAdded(AccountRelationship accountRelationship) {
        this.relationshipsAdded.add(accountRelationship);
        accountRelationship.setAdded(this);
    }

}
