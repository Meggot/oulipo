// Copyright (c) 2019 Travelex Ltd

package com.project.dao.entites;

import com.common.models.enums.PartStatus;
import com.common.models.enums.ProjectType;
import com.common.models.enums.SourcingType;
import com.common.models.enums.VisibilityType;
import com.project.dao.handlers.ProjectEventHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@Entity(name = "Project")
@EntityListeners(ProjectEventHandler.class)
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

    @ToString.Exclude
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectPart> partList = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<AuthorProjectRole> authorProjectRoles = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectTag> tags = new ArrayList<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    private Copy copy;

    public Optional<ProjectPart> getNextToBeInLineToWriting() {
        return partList.stream()
                .filter(part -> part.getStatus() == PartStatus.RESERVED)
                .min(Comparator.comparing(ProjectPart::getSequence));
    }

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
        Optional<ProjectPart> nextInLine = partList.stream()
                .filter(part -> part.getStatus() == PartStatus.IN_PROGRESS)
                .min(Comparator.comparing(ProjectPart::getSequence));
        if (nextInLine.isPresent()) {
            return nextInLine.get().getSequence();
        } else {
            return 1;
        }
    }
}
