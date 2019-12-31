package com.project.dao.entites;

import com.common.models.enums.CopyEditStatus;
import com.project.dao.handlers.EditEventHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(EditEventHandler.class)
public class CopyEdit extends EntityObject {

    @Id
    @SequenceGenerator(name = "copy_edit_seq", sequenceName = "copy_edit_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "copy_edit_seq")
    @Column(name = "pk_copy_edit_id")
    private Integer id;

    @JoinColumn(name = "fk_copy_id", referencedColumnName = "pk_copy_id")
    @ManyToOne
    private Copy copy;

    @Column(name = "delta")
    private String delta;

    @JoinColumn(name = "fk_author_id", referencedColumnName = "pk_author_id")
    @ManyToOne
    private Author author;

    @Column(name = "status")
    private CopyEditStatus status;
}
