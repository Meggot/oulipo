package com.project.controllers;

import com.common.models.dtos.ProjectTagDto;
import com.project.controllers.assemblers.ProjectTagAssembler;
import com.project.dao.entites.ProjectTag;
import com.project.dao.repository.ProjectTagRepository;
import com.project.services.ProjectTagManagementService;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tags")
@Slf4j
public class ProjectTagController {

    @Autowired
    ProjectTagAssembler tagAssembler;

    @Autowired
    ProjectTagRepository repository;

    @Autowired
    ProjectTagManagementService projectTagManagementService;

    @ResponseBody
    @RequestMapping(path = "/{tagId}", method = RequestMethod.GET)
    public Resource<ProjectTagDto> findTagById(@PathVariable(name = "tagId") ProjectTag tag, @RequestHeader(name = "User") String userId) {
        log.debug("Received a findTagById request for tag id {} by userId {}", tag, userId);
        return new Resource<>(tagAssembler.toResource(tag));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<ProjectTagDto> findTags(@RequestHeader("User") String userId,
                                                  @QuerydslPredicate(root = ProjectTag.class) Predicate predicate,
                                                  Pageable pageable, Model model, PagedResourcesAssembler pagedResourcesAssembler) {
        log.debug("Received a findAll request for tags by userId {}", userId);
        Page<ProjectTagDto> page = repository.findAll(predicate, pageable).map(tag -> tagAssembler.toResource(tag));
        model.addAttribute("tagDtos", page);
        return pagedResourcesAssembler.toResource(page);
    }

    @ResponseBody
    @RequestMapping(path = "/{tagId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTag(@PathVariable("tagId") ProjectTag projectTag,
                                    @RequestHeader("User") String userId) {
        log.debug("Received a delete tag request for tag {} by user {}", projectTag, userId);
        boolean hasDeleted = projectTagManagementService.handleDeleteTagRequest(projectTag, userId);
        if (hasDeleted) {
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
