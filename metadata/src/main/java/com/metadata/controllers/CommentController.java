package com.metadata.controllers;

import com.common.models.dtos.CommentDto;
import com.common.models.dtos.EntityType;
import com.common.models.requests.CommentPostRequest;
import com.metadata.controllers.assemblers.CommentAssembler;
import com.metadata.dao.entites.Comment;
import com.metadata.dao.entites.QComment;
import com.metadata.dao.repository.CommentRepository;
import com.metadata.services.CommentService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/comments")
public class CommentController {

    @Autowired
    private CommentAssembler assembler;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/{commentId}")
    public Resource<CommentDto> findById(@RequestParam(name = "commentId") Comment comment, Model model) {
        CommentDto dto = assembler.toResource(comment);
        model.addAttribute("comment", dto);
        return new Resource<>(dto);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<CommentDto> findAll(@QuerydslPredicate(root = Comment.class) Predicate predicate,
                                              Pageable pageable,
                                              Model model,
                                              PagedResourcesAssembler pagedResourcesAssembler) {
        Page<CommentDto> dtos = commentRepository.findAll(predicate, pageable).map(entity -> assembler.toResource((Comment) entity));
        model.addAttribute(dtos);
        return pagedResourcesAssembler.toResource(dtos);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/{entityType}/{entityId}")
    public PagedResources<CommentDto> findAllForEntityTypeAndEntityId(@PathVariable("entityType") EntityType entityType,
                                                                      @PathVariable("entityId") Integer entityId,
                                                                      Pageable pageable,
                                                                      PagedResourcesAssembler pagedResourcesAssembler) {
        Predicate onlyForTypeAndId = QComment.comment.entityType.eq(entityType).and(QComment.comment.entityId.eq(entityId));
        Page<Comment> dtos = commentRepository.findAll(onlyForTypeAndId, pageable);
        return pagedResourcesAssembler.toResource(dtos.map(assembler::toResource));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Resource<CommentDto> postComment(@ModelAttribute @Valid CommentPostRequest request, @RequestHeader("User") String userId) {
        Comment comment = commentService.postComment(request, userId);
        CommentDto commentDto = assembler.toResource(comment);
        return new Resource<>(commentDto);
    }

}
