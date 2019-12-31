package com.project.services;

import com.common.models.enums.TagType;
import com.common.models.enums.VisibilityType;
import com.common.models.requests.CreateTagRequest;
import com.project.dao.entites.Author;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectTag;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.ProjectRepository;
import com.project.dao.repository.ProjectTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class AdminActionManagementService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectTagRepository projectTagRepository;

    @Autowired
    AuthorRepository authorRepository;

    public Project handleArchiveProject(Project project, String adminUserId) {
        project.setVisibilityType(VisibilityType.ARCHIVED);
        return projectRepository.save(project);
    }

    public ProjectTag handleAdminTag(CreateTagRequest createTagRequest, Project project, String adminUserId) {
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(adminUserId))
                .orElseThrow(() -> new NoSuchElementException("Admin Author doesn't exist with ID " + adminUserId));
        ProjectTag newTag = new ProjectTag();
        newTag.setType(TagType.ADMIN_ADDED);
        newTag.setValue(createTagRequest.getValue());
        project.addTag(newTag);
        author.addAuthorCreatedTag(newTag);
        return projectTagRepository.save(newTag);
    }
}
