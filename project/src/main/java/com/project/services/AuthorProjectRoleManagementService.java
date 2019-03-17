package com.project.services;

import com.common.models.requests.AuthorProjectRoleRequest;
import com.common.models.requests.UpdateAuthorProjectRole;
import com.project.dao.entites.Author;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.entites.Project;
import com.project.dao.repository.AuthorProjectRoleRepository;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.ProjectRepository;
import com.project.services.permissions.AuthorRolePermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class AuthorProjectRoleManagementService {

    private AuthorProjectRoleRepository authorProjectRoleRepository;

    private AuthorRepository authorRepository;

    private ProjectRepository projectRepository;

    private AuthorRolePermissions authorRolePermissionsEngine;

    @Autowired
    public AuthorProjectRoleManagementService(AuthorProjectRoleRepository authorProjectRoleRepository,
                                              AuthorRepository authorRepository,
                                              ProjectRepository projectRepository,
                                              AuthorRolePermissions authorRolePermissions) {
        this.authorProjectRoleRepository = authorProjectRoleRepository;
        this.authorRepository = authorRepository;
        this.projectRepository = projectRepository;
        this.authorRolePermissionsEngine = authorRolePermissions;

    }

    public AuthorProjectRole handleCreateAuthorProjectRoleRequest(AuthorProjectRoleRequest authorProjectRoleRequest,
                                                                  String userId) {
        Author requestedAuthor = authorRepository.findAuthorByUserIdEquals(authorProjectRoleRequest.getUserId())
                .orElseThrow(() -> new NoSuchElementException("A author by userId " + authorProjectRoleRequest.getUserId() + " does not exist"));
        Project project = projectRepository.findById(authorProjectRoleRequest.getProjectId())
                .orElseThrow(() -> new NoSuchElementException("A project by projectId " + authorProjectRoleRequest.getProjectId() + " does not exist"));

        Optional<AuthorProjectRole> existingAuthorProjectRole = project.getAuthorProjectRoles().stream()
                .filter(authorsExisting -> authorsExisting.getAuthor().getUserId().equals(authorProjectRoleRequest.getUserId()))
                .findFirst();

        if (existingAuthorProjectRole.isPresent()) {
            throw new RuntimeException("A project role for that user " + authorProjectRoleRequest.getUserId() + " already exists");
        }

        if (authorRolePermissionsEngine.canUserRolePostRole(getAuthorRoleOfProjectById(project.getId(), userId).getRole(), authorProjectRoleRequest.getAuthorProjectRoleType())) {
            AuthorProjectRole authorProjectRole = new AuthorProjectRole();
            authorProjectRole.setProject(project);
            authorProjectRole.setAuthor(requestedAuthor);
            authorProjectRole.setRole(authorProjectRoleRequest.getAuthorProjectRoleType());
            authorProjectRole = authorProjectRoleRepository.save(authorProjectRole);
            return authorProjectRole;
        }
        return null;
    }

    public AuthorProjectRole handleUpdateAuthorProjectRoleRequest(AuthorProjectRole authorProjectRole, UpdateAuthorProjectRole request, String userId) {
        if (authorRolePermissionsEngine.canUserRolePatchRole(getAuthorRoleOfProjectById(authorProjectRole.getProject().getId(), userId).getRole(),
                request.getNewRole(),
                authorProjectRole.getRole())) {
            authorProjectRole.setRole(request.getNewRole());
            return authorProjectRoleRepository.save(authorProjectRole);
        }
        return null;
    }

    private AuthorProjectRole getAuthorRoleOfProjectById(Integer projectId, String userId) {
        Author requestingAuthor = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseThrow(() -> new NoSuchElementException("An author by userId " + userId + " does not exist"));
        return requestingAuthor.getAuthorProjectRoles()
                .stream()
                .filter(authorProjectRole -> authorProjectRole.getProject().getId().equals(projectId))
                .findFirst().orElseThrow(() -> new RuntimeException("An author " + userId
                        + " tried to post a new author project role for project " + projectId + " but they do not have a role"));
    }
}
