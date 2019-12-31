package com.user.services;

import com.common.models.enums.GroupRole;
import com.common.models.exceptions.InternalServerException;
import com.common.models.exceptions.UnauthorizedException;
import com.common.models.requests.PatchGroupRequest;
import com.common.models.requests.PostAccountGroupMembershipRequest;
import com.common.models.requests.PostGroupRequest;
import com.common.models.requests.PostProjectMemberRequest;
import com.user.dao.entites.Account;
import com.user.dao.entites.AccountGroupMembership;
import com.user.dao.entites.Group;
import com.user.dao.entites.ProjectGroupMembership;
import com.user.dao.repository.AccountGroupMembershipRepository;
import com.user.dao.repository.GroupRepository;
import com.user.dao.repository.ProjectGroupMembershipRepository;
import com.user.services.permissions.GroupPermissionsEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class GroupManagementService {

    @Autowired
    private GroupPermissionsEngine permissionsEngine;

    @Autowired
    private GroupRepository repository;

    @Autowired
    private AccountGroupMembershipRepository accountGroupMembershipRepository;

    @Autowired
    private ProjectGroupMembershipRepository projectGroupMembershipRepository;

    public Group handlePostGroupRequest(PostGroupRequest request,
                                        Account creator) {
        log.info(">[CREATION] Handling post group request {} from account ID: {}", request, creator.getId());
        Group group = new Group();
        group.setDescription(request.getDescription());
        group.setName(request.getName());
        group.setType(request.getType());
        group = repository.save(group);
        //Add Creator Role.
        AccountGroupMembership creatorMembership = new AccountGroupMembership();
        creator.addGroupMembership(creatorMembership);
        group.addMember(creatorMembership);
        creatorMembership.setAddedBy(null);
        creatorMembership.setRole(GroupRole.LEADER);
        accountGroupMembershipRepository.save(creatorMembership);

        return group;
    }

    public Group handlePatchGroupRequest(PatchGroupRequest patchGroupRequest, Group group, Account account) {
        log.info(">[UPDATE] Handling update group request {} on group ID: {} from account ID: {}", patchGroupRequest, group.getId(), account.getId());
        AccountGroupMembership accountMembership = getAccountMembershipOptional(group, account)
                .orElseThrow(() -> new UnauthorizedException("Account " + account.getId() + " tried to patch group " + group.getId() + " where it has no membership"));
        if (!permissionsEngine.canUserRolePatchGroup(accountMembership.getRole())) {
            throw new UnauthorizedException("You cannot patch that group.");
        }
        if (patchGroupRequest.getDescription() != null) {
            group.setDescription(patchGroupRequest.getDescription());
        }
        if (patchGroupRequest.getName() != null) {
            group.setName(patchGroupRequest.getName());
        }
        if (patchGroupRequest.getType() != null) {
            group.setType(patchGroupRequest.getType());
        }
        return repository.save(group);
    }

    public AccountGroupMembership handlePostAccountGroupMembershipRequest(PostAccountGroupMembershipRequest request, Account addedBy, Account added, Group group) {
        AccountGroupMembership accountGroupMembership = getAccountMembershipOptional(group, addedBy)
                .orElseThrow(() -> new UnauthorizedException("Account " + addedBy.getId() + " tried to post an account membership " + group.getId() + " where it has no membership"));
        if (getAccountMembershipOptional(group, added).isPresent()) {
            throw new InternalServerException("Cannot post an account membership when they already have one. Try PATCH");
        }
        if (!permissionsEngine.canUserRoleAddRoleType(accountGroupMembership.getRole(), request.getRole())) {
            throw new UnauthorizedException("You cannot add that type of role.");
        }
        AccountGroupMembership newMembership = new AccountGroupMembership();
        newMembership.setRole(request.getRole());
        group.addMember(newMembership);
        added.addGroupMembership(newMembership);
        newMembership.setAddedBy(addedBy);
        return accountGroupMembershipRepository.save(newMembership);
    }

    private Optional<AccountGroupMembership> getAccountMembershipOptional(Group group, Account account) {
        return account.getGroups()
                .stream()
                .filter(membership -> membership.getGroup().getId().equals(group.getId()))
                .findFirst();
    }

    public ProjectGroupMembership handlePostProjectMemberRequest(PostProjectMemberRequest postProjectMemberRequest, Group group, Account addedBy) {
        AccountGroupMembership accountGroupMembership = getAccountMembershipOptional(group, addedBy)
                .orElseThrow(()  -> new UnauthorizedException("Account " + addedBy.getId() + " tried to post a project membership " + group.getId() + " where it has no membership"));
        if (!permissionsEngine.canUserRoleAddProject(accountGroupMembership.getRole())) {
            throw new UnauthorizedException("You cannot post a project membership.");
        }
        ProjectGroupMembership projectGroupMembership = new ProjectGroupMembership();
        projectGroupMembership.setProjectId(postProjectMemberRequest.getProjectId());
        projectGroupMembership.setProjectName(postProjectMemberRequest.getProjectName());
        group.addProject(projectGroupMembership);
        projectGroupMembership.setAddedBy(addedBy);
        return projectGroupMembershipRepository.save(projectGroupMembership);
    }
}
