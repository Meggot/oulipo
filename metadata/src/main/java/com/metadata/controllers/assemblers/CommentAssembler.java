package com.metadata.controllers.assemblers;

import com.common.models.dtos.CommentDto;
import com.metadata.controllers.CommentController;
import com.metadata.dao.entites.Comment;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CommentAssembler extends ResourceAssemblerSupport<Comment, CommentDto> {

    CommentAssembler() {
        super(CommentController.class, CommentDto.class);
    }


    @Override
    public CommentDto toResource(Comment comment) {
        CommentDto dto = createResourceWithId(comment.getId(), comment);
        dto.setCreationDate(comment.getCreationDate().toString());
        dto.setEntityId(comment.getEntityId());
        dto.setIdField(comment.getId());
        dto.setUserId(comment.getUserId());
        dto.setValue(comment.getValue());
        return dto;
    }
}
