package com.project.controllers.assemblers;

import com.common.models.dtos.AuthorDto;
import com.project.controllers.AuthorController;
import com.project.dao.entites.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorAssembler extends ResourceAssemblerSupport<Author, AuthorDto> {

    @Autowired
    ProjectAssembler projectAssembler;

    @Autowired
    PartAssembler partAssembler;

    public AuthorAssembler() {
        super(AuthorController.class, AuthorDto.class);
    }

    @Override
    public AuthorDto toResource(Author author) {
        AuthorDto dto = createResourceWithId(author.getAuthorId(), author);
        dto.setIdField(author.getAuthorId());
        dto.setUserId(author.getUserId());
        dto.setUsername(author.getUsername());
        dto.setCreatedProjects(author.getCreatedProjects().stream()
                .map(projectAssembler::toResource).collect(Collectors.toList()));
        dto.setCreatedParts(author.getCreatedParts().stream()
                .map(partAssembler::toResource).collect(Collectors.toList()));
        return dto;
    }
}
