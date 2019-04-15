package com.project.controllers;

import com.common.models.dtos.AuthorProjectRoleDto;
import com.common.models.requests.AuthorProjectRoleRequest;
import com.common.models.requests.UpdateAuthorProjectRole;
import com.project.controllers.assemblers.AuthorProjectRoleAssembler;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.repository.AuthorProjectRoleRepository;
import com.project.services.AuthorProjectRoleManagementService;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping(path = "/roles")
public class AuthorProjectRoleController {

    @Autowired
    private AuthorProjectRoleAssembler assembler;

    @Autowired
    private AuthorProjectRoleRepository repository;

    @Autowired
    private AuthorProjectRoleManagementService authorProjectRoleManagementService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/{roleId}")
    Resource<AuthorProjectRoleDto> findById(@PathVariable("roleId") AuthorProjectRole authorProjectRole,
                                            @RequestHeader("User") String userId,
                                            Model model) {
        log.debug("{} sent a GET request for roleId {}", userId, authorProjectRole.getId());
        model.addAttribute("authorProjectRole", authorProjectRole);
        return new Resource<>(assembler.toResource(authorProjectRole));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    PagedResources<AuthorProjectRoleDto> findAll(@QuerydslPredicate(root = AuthorProjectRole.class) Predicate predicate,
                                                 @RequestHeader("User") String userId,
                                                 Pageable pageable,
                                                 Model model,
                                                 PagedResourcesAssembler pagedResourcesAssembler) {
        log.debug("{} sent a GET all request, using predicate [{}] and pageable [{}]", userId, predicate, pageable);
        Page<AuthorProjectRoleDto> page = repository.findAll(predicate, pageable).map(entity -> assembler.toResource(entity));
        model.addAttribute("authorProjectRolePage", page);
        return pagedResourcesAssembler.toResource(page);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    Resource<AuthorProjectRoleDto> postRole(@ModelAttribute @Valid AuthorProjectRoleRequest request, Model model, @RequestHeader("User") String userId) {
        log.debug("{} sent an AuthorProjectRoleRequest {}", userId, request);
        AuthorProjectRole authorProjectRole = authorProjectRoleManagementService.handleCreateAuthorProjectRoleRequest(request, userId);
        model.addAttribute("authorProjectRole", authorProjectRole);
        return new Resource<>(assembler.toResource(authorProjectRole));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH, path = "/{roleId}")
    Resource<AuthorProjectRoleDto> patchRole(@PathVariable("roleId") AuthorProjectRole role,
                                            @ModelAttribute @Valid UpdateAuthorProjectRole request,
                                             Model model,
                                             @RequestHeader("User") String userId) {
        log.debug("{} sent an UpdateAuthorRoleRequest {}", userId, request);
        AuthorProjectRole authorProjectRole = authorProjectRoleManagementService.handleUpdateAuthorProjectRoleRequest(role, request, userId);
        model.addAttribute("authorProjectRole", authorProjectRole);
        return new Resource<>(assembler.toResource(authorProjectRole));
    }
}
