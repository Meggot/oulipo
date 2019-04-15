// Copyright (c) 2019 Travelex Ltd

package com.project.dao.entites;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Copy")
@Data
public class Copy extends EntityObject{

    @Id
    @SequenceGenerator(name = "copy_seq", sequenceName = "copy_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "copy_seq")
    @Column(name = "pk_copy_id")
    private Integer id;

    @JoinColumn(name = "project_id")
    @OneToOne
    private Project project;

    @Column(name = "value", length = 10000)
    private String value;

    @OneToMany(mappedBy = "copy")
    private List<CopyEdit> edits;

    public void addCopyEdit(CopyEdit copyEdit) {
        this.edits.add(copyEdit);
        copyEdit.setCopy(this);
    }
}
