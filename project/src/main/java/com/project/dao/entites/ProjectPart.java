// Copyright (c) 2019 Travelex Ltd

package com.project.dao.entites;

import com.common.models.enums.PartStatus;
import com.project.dao.handlers.PartEventHandler;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@EntityListeners(PartEventHandler.class)
public class ProjectPart extends EntityObject {

    @Id
    @SequenceGenerator(name = "project_part_seq", sequenceName = "project_part_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_part_seq")
    @Column(name = "pk_project_part_id")
    private Integer id;

    @Column(name = "sequence")
    private Integer sequence;

    @JoinColumn(name = "fk_holding_author_id")
    @OneToOne
    private Author currentlyHoldingAuthor;

    @Column(name = "status")
    private PartStatus status;

    @Column(name = "value")
    private String value;

    @JoinColumn(name = "fk_project_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;
}
