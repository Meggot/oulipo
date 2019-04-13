package com.user.controllers.assemblers;

import com.common.models.dtos.GroupDto;
import com.user.controllers.GroupsController;
import com.user.dao.entites.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GroupAssembler extends ResourceAssemblerSupport<Group, GroupDto> {

    @Autowired
    private AccountGroupMembershipAssembler accountGroupMembershipAssembler;

    @Autowired
    private ProjectGroupMembershipAssembler projectGroupMembershipAssembler;

    public GroupAssembler() {
        super(GroupsController.class, GroupDto.class);
    }

    @Override
    public GroupDto toResource(Group group) {
        GroupDto dto = createResourceWithId(group.getId(), group);
        dto.setDescription(group.getDescription());
        dto.setIdField(group.getId());
        dto.setName(group.getName());
        dto.setType(group.getType());
        dto.setMembers(group.getMembers().stream()
                .map(accountGroupMembershipAssembler::toResource)
                .collect(Collectors.toList()));
        dto.setProjects(group.getProjects().stream()
                .map(projectGroupMembershipAssembler::toResource)
                .collect(Collectors.toList()));
        return dto;
    }
}
