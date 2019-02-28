// Copyright (c) 2019 Travelex Ltd

package com.project.controllers.assemblers;

import com.common.models.dtos.ProjectDto;
import com.project.controllers.ProjectController;
import com.project.dao.entites.Project;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProjectAssembler extends ResourceAssemblerSupport<Project, ProjectDto> {

    public ProjectAssembler() {
        super(ProjectController.class, ProjectDto.class);
    }

    @Override
    public ProjectDto toResource(Project project) {
        ProjectDto dto = createResourceWithId(project.getId(), project);
        dto.setSynopsis(project.getSynopsis());
        dto.setTitle(project.getTitle());
        return dto;
    }
}
