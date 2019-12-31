package com.user.controllers;

import com.common.models.dtos.GroupDto;
import com.common.models.dtos.ProjectGroupMembershipDto;
import com.common.models.requests.PatchGroupRequest;
import com.common.models.requests.PostGroupRequest;
import com.common.models.requests.PostProjectMemberRequest;
import com.querydsl.core.types.Predicate;
import com.user.controllers.assemblers.GroupAssembler;
import com.user.controllers.assemblers.ProjectGroupMembershipAssembler;
import com.user.dao.entites.Account;
import com.user.dao.entites.Group;
import com.user.dao.entites.ProjectGroupMembership;
import com.user.dao.entites.QGroup;
import com.user.dao.repository.GroupRepository;
import com.user.services.GroupManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.common.models.enums.GroupType.OPEN;

@RestController
@RequestMapping(produces = "application/json", path = "/groups")
public class GroupsController {

    @Autowired
    private GroupAssembler assembler;

    @Autowired
    private ProjectGroupMembershipAssembler projectGroupMembershipAssembler;

    @Autowired
    private GroupRepository repository;

    @Autowired
    private GroupManagementService groupManagementService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<GroupDto> findAll(@QuerydslPredicate(root = Group.class) Predicate predicate,
                                            Pageable pageable,
                                            PagedResourcesAssembler pagedResourcesAssembler,
                                            @RequestHeader("User") Account user) {
        Predicate onlyOpenToo = QGroup.group.type.eq(OPEN).and(predicate);
//        Predicate notBarred = QGroup.group.members.any().role.notIn(GroupRole.BARRED).and(onlyOpen);
//        Predicate alsoInvited = QGroup.group.type.eq(GroupType.PRIVATE).when(QGroup.group.members.any().id.eq(user.getId()));
        Page<GroupDto> page = repository.findAll(onlyOpenToo, pageable).map(assembler::toResource);
        return pagedResourcesAssembler.toResource(page);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Resource<GroupDto> postGroup(@RequestHeader("User") Account account,
                                        @Valid @ModelAttribute("PostGroupRequest") PostGroupRequest postGroupRequest) {
        Group group = groupManagementService.handlePostGroupRequest(postGroupRequest, account);
        return new Resource<>(assembler.toResource(group));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/{groupId}")
    public Resource<GroupDto> getGroupId(@RequestHeader("User") Account account,
                                         @PathVariable("groupId") Group group) {
        return new Resource<>(assembler.toResource(group));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH, path = "/{groupId}")
    public Resource<GroupDto> patchGroupById(@RequestHeader("User") Account account,
                                             @PathVariable("groupId") Group group,
                                             @Valid @ModelAttribute("PatchGroupRequest") PatchGroupRequest patchGroupRequest) {
        group = groupManagementService.handlePatchGroupRequest(patchGroupRequest, group, account);
        return new Resource<>(assembler.toResource(group));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/{groupId}/projects")
    public Resource<ProjectGroupMembershipDto> postProjectMembership(@RequestHeader("User") Account addedBy,
                                                                     @PathVariable("groupId") Group group,
                                                                     @Valid @ModelAttribute("PostProjectMemberRequest") PostProjectMemberRequest postProjectMemberRequest) {
        ProjectGroupMembership membership = groupManagementService.handlePostProjectMemberRequest(postProjectMemberRequest, group, addedBy);
        return new Resource<>(projectGroupMembershipAssembler.toResource(membership));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/project/{projectId}")
    public PagedResources<GroupDto> getGroupsWhereProjectIsMember(@RequestHeader("User") Account addedBy,
                                                                  Pageable pageable,
                                                                  @PathVariable("projectId") Integer projectId,
                                                                  PagedResourcesAssembler pagedResourcesAssembler) {
        Predicate whereProjectIdIsMember = QGroup.group.projects.any().projectId.eq(projectId);
        Page<GroupDto> page = repository.findAll(whereProjectIdIsMember, pageable).map(assembler::toResource);
        return pagedResourcesAssembler.toResource(page);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/search")
    public PagedResources<GroupDto> getGroupsWhereGroupNameContains(@RequestHeader("User") Account requester,
                                                                    Pageable pageable,
                                                                    @RequestParam(name = "name") String groupName,
                                                                    PagedResourcesAssembler pagedResourcesAssembler) {
        Predicate whereGroupNameContains = QGroup.group.name.containsIgnoreCase(groupName);
        Page<GroupDto> page = repository.findAll(whereGroupNameContains, pageable).map(assembler::toResource);
        return pagedResourcesAssembler.toResource(page);
    }
}
