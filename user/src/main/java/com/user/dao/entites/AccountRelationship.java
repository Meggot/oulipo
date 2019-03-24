package com.user.dao.entites;

import com.common.models.dtos.AccountRelationshipStatus;
import com.common.models.dtos.AccountRelationshipType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "AccountRelationship")
@Data
@NoArgsConstructor
public class AccountRelationship extends EntityObject {

    @Id
    @SequenceGenerator(name = "account_relationship_seq", sequenceName = "account_relationship_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_relationship_seq")
    @Column(name = "pk_account_relationship_id")
    private int id;

    @JoinColumn(name = "fk_added_by_account_id")
    @ManyToOne
    private Account addedBy;

    @JoinColumn(name = "fk_added_account_id")
    @ManyToOne
    private Account added;

    @Column(name="status")
    private AccountRelationshipStatus status;

    @Column(name = "relationship_type")
    private AccountRelationshipType relationshipType;

}
