// Copyright (c) 2019 Travelex Ltd

package com.project.controllers;

import com.common.models.dtos.ProjectDto;
import com.common.models.requests.CreateProject;
import com.common.models.requests.UpdateProject;
import com.project.controllers.assemblers.ProjectAssembler;
import com.project.dao.entites.Project;
import com.project.dao.repository.ProjectRepository;
import com.project.services.ProjectManagementService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(produces = "application/json")
public class ProjectController {

    @Autowired
    ProjectAssembler projectAssembler;

    @Autowired
    ProjectManagementService projectManagementService;

    @Autowired
    ProjectRepository projectRepository;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<PagedResources<ProjectDto>> findAll(@QuerydslPredicate(root = Project.class) Predicate predicate,
                                                          Pageable pageable,
                                                          Model model,
                                                          PagedResourcesAssembler pagedResourcesAssembler) {
        Page<ProjectDto> projectPage = projectRepository.findAll(predicate, pageable).map(project -> projectAssembler.toResource(project));
        model.addAttribute("projects", projectPage);
        return new ResponseEntity(pagedResourcesAssembler.toResource(projectPage), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public Resource<ProjectDto> findById(@PathVariable("projectId") Project project, Model model) {
        ProjectDto dto = projectAssembler.toResource(project);
        model.addAttribute("project", dto);
        return new Resource<>(dto);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Resource<ProjectDto> createProject(@RequestHeader("User") String userId,
                                              @Valid @ModelAttribute("CreateProject") CreateProject createProject,
                                              Model model) {
        Project project = projectManagementService.createProject(userId, createProject);
        ProjectDto dto = projectAssembler.toResource(project);
        model.addAttribute("project", dto);
        return new Resource<>(dto);
    }

    @ResponseBody
    @RequestMapping(value = "/{projectId}", method = RequestMethod.PATCH)
    public Resource<ProjectDto> updateAProject(@PathVariable("projectId") Project project,
                                               @RequestHeader("User") String userId,
                                               @Valid @ModelAttribute("UpdateProject") UpdateProject updateProject,
                                               Model model){
        Project updatedProject = projectManagementService.updateProject(project, userId, updateProject);
        ProjectDto updateProjectDto = projectAssembler.toResource(updatedProject);
        model.addAttribute("project", updateProjectDto);
        return new Resource<>(updateProjectDto);
    }
}
