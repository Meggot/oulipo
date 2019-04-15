package com.user.dao.entites;

import com.common.models.dtos.AccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "Account")
@Data
@NoArgsConstructor
public class Account extends EntityObject {

    @Id
    @SequenceGenerator(name = "accounts_seq", sequenceName = "accounts_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_seq")
    @Column(name = "pk_account_id")
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
    private List<AccountGroupMembership> groups;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountLogin> logins = new ArrayList<>();

    @OneToMany(mappedBy = "addedBy")
    private List<AccountRelationship> relationshipsAddedBy = new ArrayList<>();

    @OneToMany(mappedBy = "added")
    private List<AccountRelationship> relationshipsAdded = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<AccountTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages = new ArrayList<>();

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

    public void addMessageReceived(Message message) {
        this.receivedMessages.add(message);
        message.setRecipient(this);
    }

    public void addMessageSent(Message message) {
        this.sentMessages.add(message);
        message.setSender(this);
    }

    public void addGroupMembership(AccountGroupMembership membership) {
        this.groups.add(membership);
        membership.setAccount(this);
    }

}
