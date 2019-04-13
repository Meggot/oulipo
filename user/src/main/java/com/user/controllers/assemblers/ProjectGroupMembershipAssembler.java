package com.user.controllers.assemblers;

import com.common.models.dtos.ProjectGroupMembershipDto;
import com.user.controllers.GroupsController;
import com.user.dao.entites.ProjectGroupMembership;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProjectGroupMembershipAssembler extends ResourceAssemblerSupport<ProjectGroupMembership, ProjectGroupMembershipDto> {
    public ProjectGroupMembershipAssembler() {
        super(GroupsController.class, ProjectGroupMembershipDto.class);
    }

    @Override
    public ProjectGroupMembershipDto toResource(ProjectGroupMembership projectGroupMembership) {
        ProjectGroupMembershipDto dto = createResourceWithId(projectGroupMembership.getId(), projectGroupMembership);
        dto.setAddedById(projectGroupMembership.getAddedBy().getId());
        dto.setAddedByUsername(projectGroupMembership.getAddedBy().getUsername());
        dto.setGroupId(projectGroupMembership.getGroup().getId());
        dto.setGroupName(projectGroupMembership.getGroup().getName());
        dto.setIdField(projectGroupMembership.getId());
        dto.setProjectId(projectGroupMembership.getProjectId());
        dto.setProjectName(projectGroupMembership.getProjectName());
        dto.setAdded(projectGroupMembership.getCreationDate());
        return dto;
    }
}
