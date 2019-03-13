package com.project.controllers.assemblers;

import com.common.models.dtos.AuthorProjectRoleDto;
import com.project.controllers.AuthorProjectRoleController;
import com.project.dao.entites.AuthorProjectRole;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class AuthorProjectRoleAssembler extends ResourceAssemblerSupport<AuthorProjectRole, AuthorProjectRoleDto> {
    public AuthorProjectRoleAssembler() {
        super(AuthorProjectRoleController.class, AuthorProjectRoleDto.class);
    }

    @Override
    public AuthorProjectRoleDto toResource(AuthorProjectRole authorProjectRole) {
        AuthorProjectRoleDto dto = createResourceWithId(authorProjectRole.getId(), authorProjectRole);
        dto.setAuthorId(authorProjectRole.getAuthor().getAuthorId());
        dto.setAuthorName(authorProjectRole.getAuthor().getUsername());
        dto.setIdField(authorProjectRole.getId());
        dto.setProjectId(authorProjectRole.getProject().getId());
        dto.setProjectTitle(authorProjectRole.getProject().getTitle());
        dto.setRole(authorProjectRole.getRole());
        return dto;
    }
}
