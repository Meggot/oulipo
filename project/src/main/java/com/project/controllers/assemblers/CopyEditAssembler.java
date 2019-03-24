package com.project.controllers.assemblers;

import com.common.models.dtos.CopyEditDto;
import com.project.controllers.CopyController;
import com.project.dao.entites.CopyEdit;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CopyEditAssembler extends ResourceAssemblerSupport<CopyEdit, CopyEditDto> {

    public CopyEditAssembler() {
        super(CopyController.class, CopyEditDto.class);
    }

    @Override
    public CopyEditDto toResource(CopyEdit copyEdit) {
        CopyEditDto dto = createResourceWithId(copyEdit.getId(), copyEdit);
        dto.setAuthorName(copyEdit.getAuthor().getUsername());
        dto.setDelta(copyEdit.getDelta());
        dto.setIdField(copyEdit.getId());
        dto.setProjectTitle(copyEdit.getCopy().getProject().getTitle());
        dto.setStatus(copyEdit.getStatus());
        return dto;
    }
}
