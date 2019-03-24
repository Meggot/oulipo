package com.project.controllers.assemblers;

import com.common.models.dtos.CopyDto;
import com.project.controllers.CopyController;
import com.project.dao.entites.Copy;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CopyAssembler extends ResourceAssemblerSupport<Copy, CopyDto> {

    @Autowired
    private CopyEditAssembler copyEditAssembler;

    public CopyAssembler() {
        super(CopyController.class, CopyDto.class);
    }

    @Override
    public CopyDto toResource(Copy copy) {
        CopyDto dto = createResourceWithId(copy.getId(), copy);
        dto.setIdField(copy.getId());
        dto.setProjectId(copy.getProject().getId());
        dto.setValue(copy.getValue());
        if (copy.getEdits() != null) {
            dto.setEdits(copy.getEdits().stream()
                    .map(copyEditAssembler::toResource)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
