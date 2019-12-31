package com.user.dao.entites;

import com.common.models.enums.GroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name= "Groups")
public class Group extends EntityObject {

    @Id
    @Column(name = "pk_group_id")
    @SequenceGenerator(name = "groups_seq", sequenceName = "groups_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_seq")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private GroupType type;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountGroupMembership> members = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectGroupMembership> projects = new ArrayList<>();

    public void addMember(AccountGroupMembership accountGroupMembership) {
        members.add(accountGroupMembership);
        accountGroupMembership.setGroup(this);
    }

    public void addProject(ProjectGroupMembership projectGroupMembership) {
        projects.add(projectGroupMembership);
        projectGroupMembership.setGroup(this);
    }
}
