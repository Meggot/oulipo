package com.project.controllers;

import com.common.models.dtos.ProjectDto;
import com.common.models.enums.ProjectType;
import com.common.models.requests.SearchSortType;
import com.project.controllers.assemblers.ProjectAssembler;
import com.project.dao.entites.Project;
import com.project.dao.repository.ProjectRepository;
import com.project.services.ExplorerService;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/explore")
@Slf4j
public class ExplorerController {

    @Autowired
    ExplorerService explorerService;

    @Autowired
    ProjectAssembler projectAssembler;

    @Autowired
    ProjectRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<ProjectDto> search(@RequestHeader("User") Integer userId,
                                             @RequestParam(value = "title", required = false) String title,
                                             @RequestParam(value = "tags", required = false) List<String> tagValues,
                                             @RequestParam(value = "author", required = false) String authorName,
                                             @RequestParam(value = "type", required = false) ProjectType type,
                                             @RequestParam(value = "choice", required = false) SearchSortType sortType,
                                             @RequestParam(value = "editable", required = false) boolean onlyWhereUserCanEdit,
                                             @RequestParam(value = "oulipo", required = false) boolean onlyCurtailed,
                                             @RequestParam(value = "private", required = false) boolean onlyPrivateAndNotBarred,
                                             Pageable pageable,
                                             PagedResourcesAssembler pagedResourcesAssembler) {
        log.info(">[SEARCH] Received a search request from user id: {} with params:" +
                "[title,{}], " +
                "[tags,{}], " +
                "[author,{}], " +
                "[type,{}]," +
                "[choice,{}]," +
                "[editable,{}]," +
                "[oulipo,{}]," +
                "[private,{}]", userId, title, tagValues, authorName, type, sortType, onlyWhereUserCanEdit, onlyCurtailed, onlyPrivateAndNotBarred);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(explorerService.findByPartialTitle(title));
        booleanBuilder.and(explorerService.findWhereUserTagsExist(tagValues));
        booleanBuilder.and(explorerService.findWhereHasAuthor(authorName));
        booleanBuilder.and(explorerService.findWhereType(type));
        booleanBuilder.and(explorerService.findWhereSystemTagExist(sortType));
        Page<Project> page = repository.findAll(booleanBuilder, pageable);
        return pagedResourcesAssembler.toResource(page, projectAssembler);
    }

}
