package com.project.controllers.assemblers;

import com.common.models.dtos.CopyDto;
import com.project.controllers.CopyController;
import com.project.dao.entites.Copy;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CopyAssembler extends ResourceAssemblerSupport<Copy, CopyDto> {

    public CopyAssembler() {
        super(CopyController.class, CopyDto.class);
    }

    @Override
    public CopyDto toResource(Copy copy) {
        CopyDto dto = createResourceWithId(copy.getId(), copy);
        dto.setIdField(copy.getId());
        dto.setProjectId(copy.getProject().getId());
        dto.setValue(copy.getValue());
        return dto;
    }
}
