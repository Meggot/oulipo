// Copyright (c) 2019 Travelex Ltd

package com.project.dao.entites;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;

@Entity(name = "Author")
@Data
public class Author extends EntityObject{

    @Id
    @SequenceGenerator(name = "authors_seq", sequenceName = "authors_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authors_seq")
    @Column(name = "pk_author_id")
    private Integer authorId;

    @Column(name = "fk_user_id")
    private Integer userId;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "originalAuthor", cascade = CascadeType.ALL)
    private List<Project> createdProjects = new ArrayList<>();

    @OneToMany(mappedBy = "currentlyHoldingAuthor", cascade = CascadeType.ALL)
    private List<ProjectPart> createdParts = new ArrayList<>();

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL)
    private List<ProjectTag> createdTags = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<AuthorProjectRole> authorProjectRoles = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<CopyEdit> copyEdits = new ArrayList<>();

    public void addCopyEdit(CopyEdit copyEdit) {
        this.copyEdits.add(copyEdit);
        copyEdit.setAuthor(this);
    }
    public void addAuthorCreatedTag(ProjectTag tag) {
        this.createdTags.add(tag);
        tag.setOrigin(this);
    }

    public void addAuthorProjectRole(AuthorProjectRole authorProjectRole) {
        this.authorProjectRoles.add(authorProjectRole);
        authorProjectRole.setAuthor(this);
    }

    public void addCreatedProject(Project project) {
        this.createdProjects.add(project);
        project.setOriginalAuthor(this);
    }

    public void addCreatedPart(ProjectPart part) {
        this.createdParts.add(part);
        part.setCurrentlyHoldingAuthor(this);
    }
}
