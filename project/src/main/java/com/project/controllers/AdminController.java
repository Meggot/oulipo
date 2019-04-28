package com.project.controllers;

import com.common.models.dtos.ProjectDto;
import com.common.models.dtos.ProjectTagDto;
import com.common.models.requests.CreateTagRequest;
import com.project.controllers.assemblers.ProjectAssembler;
import com.project.controllers.assemblers.ProjectTagAssembler;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectTag;
import com.project.services.AdminActionManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin")
@Slf4j
public class AdminController {

    @Autowired
    AdminActionManagementService adminActionManagementService;

    @Autowired
    ProjectAssembler projectAssembler;

    @Autowired
    ProjectTagAssembler projectTagAssembler;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/projects/{projectId}/archive")
    public Resource<ProjectDto> archiveProject(@PathVariable("projectId") Project project,
                                               @RequestHeader("User") String adminUserId) {
        log.debug(">[ADMIN] {} has archived {}", adminUserId, project);
        Project projectEntity = adminActionManagementService.handleArchiveProject(project, adminUserId);
        return new Resource<>(projectAssembler.toResource(projectEntity));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/projects/{projectId}/tag")
    public Resource<ProjectTagDto> addAdminTag(@PathVariable("projectId") Project project,
            @RequestHeader("User") String adminUserId,
                                               @ModelAttribute @Valid CreateTagRequest createTagRequest) {
        log.debug(">[ADMIN] {} has added an Admin tag to {}", adminUserId, createTagRequest);
        ProjectTag tagEntity = adminActionManagementService.handleAdminTag(createTagRequest, project, adminUserId);
        return new Resource<>(projectTagAssembler.toResource(tagEntity));
    }
}
