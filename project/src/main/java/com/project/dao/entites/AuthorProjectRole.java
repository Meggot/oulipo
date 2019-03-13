package com.project.dao.entites;

import com.common.models.dtos.AuthorProjectRoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "AuthorProjectRole")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorProjectRole extends EntityObject{

    @Id
    @SequenceGenerator(name = "author_project_role_seq", sequenceName = "author_project_role_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_project_role_seq")
    @Column(name = "pk_author_project_role_id")
    private Integer id;

    @JoinColumn(name = "fk_author_id", referencedColumnName = "pk_author_id")
    @ManyToOne
    private Author author;

    @JoinColumn(name = "fk_project_id", referencedColumnName = "pk_project_id")
    @ManyToOne
    private Project project;

    @Column(name = "role")
    private AuthorProjectRoleType role;
}
