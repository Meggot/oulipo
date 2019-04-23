// Copyright (c) 2019 Travelex Ltd

package com.project.dao.entites;

import com.common.models.dtos.PartStatus;
import com.common.models.dtos.ProjectType;
import com.common.models.dtos.SourcingType;
import com.common.models.dtos.VisibilityType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity(name = "Project")
@Data
@NoArgsConstructor
public class Project extends EntityObject {

    @Id
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @Column(name = "pk_project_id")
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

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectPart> partList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<AuthorProjectRole> authorProjectRoles = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectTag> tags = new ArrayList<>();

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Copy copy;


    public void addTag(ProjectTag projectTag) {
        this.tags.add(projectTag);
        projectTag.setProject(this);
    }

    public void addAuthorProjectRole(AuthorProjectRole authorProjectRole) {
        this.authorProjectRoles.add(authorProjectRole);
        authorProjectRole.setProject(this);
    }

    public void addPart(ProjectPart part) {
        this.partList.add(part);
        part.setProject(this);
    }

    public Optional<ProjectPart> getLastAddedPart() {
        return partList.stream()
                .max(Comparator.comparing(ProjectPart::getSequence));
    }

    public Integer getNextToBePostedPartSequence() {
        return partList.stream()
                .filter(part -> part.getStatus() == PartStatus.RESERVED || part.getStatus() == PartStatus.ACTIVE)
                .min(Comparator.comparing(ProjectPart::getSequence)).get().getSequence();
    }
}
