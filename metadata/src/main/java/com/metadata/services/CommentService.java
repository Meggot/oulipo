package com.metadata.services;

import com.common.models.requests.CommentPostRequest;
import com.metadata.dao.entites.Comment;
import com.metadata.dao.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment postComment(CommentPostRequest commentPostRequest, String userId) {
        log.debug(">[CREATE] Handling create comment request {}", commentPostRequest);
        Comment comment = new Comment();
        comment.setEntityId(commentPostRequest.getEntityId());
        comment.setUserId(Integer.parseInt(userId));
        comment.setValue(commentPostRequest.getValue());
        comment.setEntityType(commentPostRequest.getEntityType());
        return commentRepository.save(comment);
    }
}
