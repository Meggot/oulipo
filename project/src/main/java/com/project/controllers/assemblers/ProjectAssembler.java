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
        dto.setProjectId(project.getId());
        dto.setSynopsis(project.getSynopsis());
        dto.setTitle(project.getTitle());
        dto.setType(project.getType().toString());
        dto.setVisibilityType(project.getVisibilityType().toString());
        dto.setSourcingType(project.getSourcingType().toString());
        dto.setOriginalAuthor(project.getOriginalAuthor().getUsername());
        dto.setCreationDate(project.getCreationDate().toString());
        dto.setModifiedDate(project.getModifiedDate().toString());
        dto.setVersion(project.getOca());
        return dto;
    }
}