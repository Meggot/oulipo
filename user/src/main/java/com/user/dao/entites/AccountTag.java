package com.user.dao.entites;

import com.common.models.dtos.AccountTagCategory;
import com.common.models.dtos.TagType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "AccountTag")
@Data
@NoArgsConstructor
public class AccountTag extends EntityObject {

    @Id
    @SequenceGenerator(name = "account_tag_seq", sequenceName = "account_tag_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_tag_seq")
    @Column(name = "pk_tag_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_account_id")
    private Account account;

    @Column(name = "tag_type")
    private TagType type;

    @Column(name = "tag_category")
    private AccountTagCategory category;

    @Column(name = "tag_value")
    private String value;

}
