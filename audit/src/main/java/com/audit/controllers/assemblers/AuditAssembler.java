// Copyright (c) 2019 Travelex Ltd

package com.audit.controllers.assemblers;

import com.audit.controllers.AuditController;
import com.audit.dao.entites.Audit;
import com.common.models.dtos.AuditDto;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class AuditAssembler extends ResourceAssemblerSupport<Audit, AuditDto> {

    public AuditAssembler() {
        super(AuditController.class, AuditDto.class);
    }

    @Override
    public AuditDto toResource(Audit audit) {
        AuditDto dto = createResourceWithId(audit.getId(), audit);
        dto.setEntityId(audit.getEntityId());
        dto.setEventType(audit.getEventType());
        dto.setOriginUserId(audit.getOriginUserId());
        dto.setIdField(audit.getId());
        dto.setValue(audit.getValue());
        dto.setTimeStamp(audit.getCreationDate());
        return dto;
    }
}
