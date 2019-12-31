package com.user.dao.entites;

import com.common.models.enums.GroupRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "AccountGroupMembership")
@Data
@NoArgsConstructor
public class AccountGroupMembership extends EntityObject {

    @Id
    @SequenceGenerator(name = "account_group_membership_seq", sequenceName = "account_group_membership_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_group_membership_seq")
    @Column(name = "pk_account_membership_id")
    private Integer id;

    @JoinColumn(name = "fk_account_id")
    @ManyToOne
    private Account account;

    @JoinColumn(name = "fk_added_by")
    @ManyToOne
    private Account addedBy;

    @JoinColumn(name = "fk_group_id")
    @ManyToOne
    private Group group;

    @Column(name = "role")
    private GroupRole role;
}
