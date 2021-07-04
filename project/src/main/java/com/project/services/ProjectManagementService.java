// Copyright (c) 2019 Travelex Ltd

package com.project.services;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.exceptions.UnauthorizedException;
import com.common.models.requests.CreateProject;
import com.common.models.requests.UpdateProject;
import com.project.dao.entites.Author;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.entites.Copy;
import com.project.dao.entites.Project;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class ProjectManagementService {

    @Autowired
    ProjectRepository projectRepository;
§
    @Autowired
    AuthorRepository authorRepository;
§
    @Autowired
    AuthorManagementService authorManagementService;

    public Project createProject(String userId, CreateProject createProject) {
        log.debug("Received create project request for user_id {} for project name {}", userId,
                createProject.getTitle());
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseGet(() -> authorManagementService.createAuthor(Integer.parseInt(userId),"Author" + userId));

        //Project Entity
        Project project = new Project();
        project.setTitle(createProject.getTitle());
        project.setSynopsis(createProject.getSynopsis());
        project.setType(createProject.getProjectType());
        project.setVisibilityType(createProject.getVisibilityType());
        project.setSourcingType(createProject.getSourcingType());
        author.addCreatedProject(project);

        //Create Copy
        Copy newCopy = new Copy();
        newCopy.setProject(project);
        newCopy.setValue("");
        project.setCopy(newCopy);

        //Initialize author as Creator
        AuthorProjectRole authorProjectRole = new AuthorProjectRole();
        authorProjectRole.setRole(AuthorProjectRoleType.CREATOR);
        authorProjectRole.setAuthor(author);
        authorProjectRole.setProject(project);
        author.addAuthorProjectRole(authorProjectRole);
        project.addAuthorProjectRole(authorProjectRole);

        //Save
        project = projectRepository.save(project);

        return project;
    }

    public Project updateProject(Project projectToUpdate, String userId, UpdateProject updateRequest) {
        if (!Objects.equals(projectToUpdate.getOriginalAuthor().getUserId(), Integer.valueOf(userId))) {
            throw new UnauthorizedException("You do not have permission to update that Project");
        }

        if (!Objects.equals(updateRequest.getTitle(), projectToUpdate.getTitle())){
            projectToUpdate.setTitle(updateRequest.getTitle());
        }
        if (!Objects.equals(updateRequest.getSynopsis(), projectToUpdate.getSynopsis())) {
            projectToUpdate.setSynopsis(updateRequest.getSynopsis());
        }

        return projectRepository.save(projectToUpdate);
    }
}
