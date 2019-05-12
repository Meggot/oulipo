package com.project.dao.entites;


import com.common.models.dtos.TagType;
import com.project.dao.handlers.TagEventHandler;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "ProjectTag")
@Data
@NoArgsConstructor
@EntityListeners(TagEventHandler.class)
public class ProjectTag extends EntityObject {

    @Id
    @SequenceGenerator(name = "project_tag_seq", sequenceName = "project_tag_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_tag_seq")
    @Column(name = "pk_tag_id")
    private Integer id;

    @JoinColumn(name = "fk_project_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;

    @Column(name = "type")
    private TagType type;

    @JoinColumn(name = "fk_author_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Author origin;

    @Column(name = "value")
    private String value;
}
