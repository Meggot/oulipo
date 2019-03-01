// Copyright (c) 2019 Travelex Ltd

package com.project.dao.entites;

import com.common.models.dtos.ProjectType;
import com.common.models.dtos.SourcingType;
import com.common.models.dtos.VisibilityType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "Project")
@Data
@NoArgsConstructor
public class Project extends EntityObject {

    @Id
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @Column(name = "project_seq")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "project_type")
    private ProjectType type;

    @Column(name = "visibility_type")
    private VisibilityType visibilityType;

    @Column(name = "sourcing_type")
    private SourcingType sourcingType;

    @JoinColumn(name = "fk_author_id")
    @ManyToOne
    private Author originalAuthor;

}
