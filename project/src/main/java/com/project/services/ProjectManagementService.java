// Copyright (c) 2019 Travelex Ltd

package com.project.services;

import com.common.models.requests.CreateProject;
import com.project.dao.entites.Author;
import com.project.dao.entites.Project;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectManagementService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    AuthorRepository authorRepository;

    public Project createProject(String userId, CreateProject createProject) {
        Project project = new Project();
        project.setTitle(createProject.getTitle());
        project.setSynopsis(createProject.getSynopsis());
        project.setType(createProject.getProjectType());
        project.setVisibilityType(createProject.getVisibilityType());
        project.setSourcingType(createProject.getSourcingType());
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId)).get();
        project.setOriginalAuthor(author);
        project = projectRepository.save(project);
        return project;
    }

}
