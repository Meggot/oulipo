// Copyright (c) 2019 Travelex Ltd

package com.project.services;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.exceptions.UnauthorizedException;
import com.common.models.messages.ProjectCreationMessage;
import com.common.models.messages.ProjectUpdateMessage;
import com.common.models.requests.CreateProject;
import com.common.models.requests.UpdateProject;
import com.project.dao.entites.Author;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.entites.Copy;
import com.project.dao.entites.Project;
import com.project.dao.repository.*;
import com.project.streaming.ProjectLifecycleStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProjectManagementService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PartRepository partRepository;

    @Autowired
    CopyRepository copyRepository;

    @Autowired
    AuthorProjectRoleRepository authorProjectRoleRepository;

    @Autowired
    ProjectLifecycleStreamer lifecycleStreamer;

    public Project createProject(String userId, CreateProject createProject) {
        Project project = new Project();
        project.setTitle(createProject.getTitle());
        project.setSynopsis(createProject.getSynopsis());
        project.setType(createProject.getProjectType());
        project.setVisibilityType(createProject.getVisibilityType());
        project.setSourcingType(createProject.getSourcingType());
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId)).get();
        author.addCreatedProject(project);
        Copy newCopy = new Copy();
        newCopy.setProject(project);
        newCopy.setValue("");
        project.setCopy(newCopy);
        AuthorProjectRole authorProjectRole = new AuthorProjectRole();
        authorProjectRole.setRole(AuthorProjectRoleType.CREATOR);
        authorProjectRole.setAuthor(author);
        authorProjectRole.setProject(project);
        author.addAuthorProjectRole(authorProjectRole);
        project.addAuthorProjectRole(authorProjectRole);
        project = projectRepository.save(project);
        ProjectCreationMessage projectCreationMessage = new ProjectCreationMessage();
        projectCreationMessage.setProjectId(String.valueOf(project.getId()));
        projectCreationMessage.setTitle(project.getTitle());
        projectCreationMessage.setUserId(userId);
        projectCreationMessage.setSynopsis(project.getSynopsis());
        lifecycleStreamer.sendProjectCreationMessage(projectCreationMessage);
        return project;
    }

    public Project updateProject(Project projectToUpdate, String userId, UpdateProject updateRequest) {
        if (!Objects.equals(projectToUpdate.getOriginalAuthor().getUserId(), Integer.valueOf(userId))) {
            throw new UnauthorizedException("You do not have permission to update that Project");
        }
        ProjectUpdateMessage projectUpdateMessage = new ProjectUpdateMessage();
        projectUpdateMessage.setOldSynopsis(projectToUpdate.getSynopsis());
        projectUpdateMessage.setNewSynopsis(updateRequest.getSynopsis());
        projectUpdateMessage.setOldTitle(projectToUpdate.getTitle());
        projectUpdateMessage.setNewTitle(updateRequest.getTitle());
        projectUpdateMessage.setProjectId(String.valueOf(projectToUpdate.getId()));
        projectUpdateMessage.setUserId(userId);
        if (!Objects.equals(updateRequest.getTitle(), projectToUpdate.getTitle())){
            projectToUpdate.setTitle(updateRequest.getTitle());
        }
        if (!Objects.equals(updateRequest.getSynopsis(), projectToUpdate.getSynopsis())) {
            projectToUpdate.setSynopsis(updateRequest.getSynopsis());
        }
        lifecycleStreamer.sendProjectUpdateMessage(projectUpdateMessage);
        return projectRepository.save(projectToUpdate);
    }
}
