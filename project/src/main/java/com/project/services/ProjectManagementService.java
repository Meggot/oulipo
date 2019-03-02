// Copyright (c) 2019 Travelex Ltd

package com.project.services;

import com.common.models.exceptions.UnauthorizedException;
import com.common.models.requests.CreateProject;
import com.common.models.requests.UpdateProject;
import com.project.dao.entites.Author;
import com.project.dao.entites.Copy;
import com.project.dao.entites.Project;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.CopyRepository;
import com.project.dao.repository.PartRepository;
import com.project.dao.repository.ProjectRepository;
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
