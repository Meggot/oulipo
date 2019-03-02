package com.project.controllers.assemblers;

import com.common.models.dtos.ProjectPartDto;
import com.project.controllers.PartController;
import com.project.dao.entites.ProjectPart;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PartAssembler extends ResourceAssemblerSupport<ProjectPart, ProjectPartDto> {

    PartAssembler() {
        super(PartController.class, ProjectPartDto.class);
    }

    @Override
    public ProjectPartDto toResource(ProjectPart part) {
        ProjectPartDto dto = createResourceWithId(part.getId(), part);
        dto.setActiveValue(part.getValue());
        dto.setIdField(part.getId());
        dto.setSequence(part.getSequence());
        dto.setProjectId(part.getProject().getId());
        return dto;
    }

}
