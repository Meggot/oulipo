package com.project.controllers.assemblers;

import com.common.models.dtos.ProjectTagDto;
import com.project.controllers.ProjectTagController;
import com.project.dao.entites.ProjectTag;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProjectTagAssembler extends ResourceAssemblerSupport<ProjectTag, ProjectTagDto> {

    public ProjectTagAssembler() {
        super(ProjectTagController.class, ProjectTagDto.class);
    }

    @Override
    public ProjectTagDto toResource(ProjectTag projectTag) {
        ProjectTagDto dto = createResourceWithId(projectTag.getId(), projectTag);
        dto.setIdField(projectTag.getId());
        dto.setProjectTitle(projectTag.getProject().getTitle());
        dto.setType(projectTag.getType());
        if (projectTag.getOrigin() != null) {
            dto.setUserIdAdded(projectTag.getOrigin().getUserId());
        }
        dto.setValue(projectTag.getValue());
        return dto;
    }
}