package com.metadata.dao.entites;

import com.common.models.dtos.EntityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Comment")
public class Comment extends EntityObject {

    @Id
    @SequenceGenerator(name = "comments_seq", sequenceName = "comments_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
    @Column(name = "pk_comment_id")
    private Integer id;

    @Column(name = "entity_id")
    private Integer entityId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "value")
    private String value;

    @Column(name= "type")
    private EntityType entityType;
}
