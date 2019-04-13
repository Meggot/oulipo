package com.user.dao.entites;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "ProjectGroupMembership")
@Data
@NoArgsConstructor
public class ProjectGroupMembership extends EntityObject {

    @Id
    @SequenceGenerator(name = "project_group_membership_seq", sequenceName = "project_group_membership_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_group_membership_seq")
    @Column(name = "pk_project_membership_id")
    private Integer id;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "project_name")
    private String projectName;

    @JoinColumn(name = "fk_group_id")
    @ManyToOne
    private Group group;

    @JoinColumn(name = "fk_added_by_id")
    @ManyToOne
    private Account addedBy;
}
