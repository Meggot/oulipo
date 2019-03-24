package com.project.services;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.dtos.TagType;
import com.common.models.exceptions.UnauthorizedException;
import com.common.models.requests.CreateTagRequest;
import com.project.dao.entites.Author;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectTag;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.ProjectRepository;
import com.project.dao.repository.ProjectTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ProjectTagManagementService {

    private AuthorRepository authorRepository;

    private ProjectRepository projectRepository;

    private ProjectTagRepository projectTagRepository;

    @Autowired
    ProjectTagManagementService(AuthorRepository authorRepository,
                                ProjectRepository projectRepository,
                                ProjectTagRepository projectTagRepository) {
        this.authorRepository = authorRepository;
        this.projectRepository = projectRepository;
        this.projectTagRepository = projectTagRepository;
    }

    public ProjectTag handleCreateTagRequest(CreateTagRequest createTagRequest, String userId) {
        //is user an author?
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseThrow(() -> new NoSuchElementException("Author by the userid " + userId + " does not exist"));
        //is user an author of the project?
        Project project = projectRepository.findById(createTagRequest.getProjectId())
                .orElseThrow(() -> new NoSuchElementException("Project by the projectid " + createTagRequest.getProjectId() + " does not exist"));
        AuthorProjectRole authorRole = project.getAuthorProjectRoles().stream().filter(tag -> tag.getAuthor().getAuthorId().equals(author.getAuthorId()))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException("Author " + author.getAuthorId() + " does not have an AuthorProjectRole with this project " + project.getId()));
        //does the user have permission to add tags only moderators or owners
        // should we refactor into its own perm engine?
        if (authorRole.getRole() != AuthorProjectRoleType.CREATOR && authorRole.getRole() != AuthorProjectRoleType.MODERATOR) {
            throw new UnauthorizedException("Author " + author.getAuthorId() + " does not have permission to post a tag");
        }
        ProjectTag newTag = new ProjectTag();
        newTag.setType(TagType.USER_ADDED);
        newTag.setValue(createTagRequest.getValue());
        project.addTag(newTag);
        author.addAuthorCreatedTag(newTag);
        newTag = projectTagRepository.save(newTag);
        return newTag;
    }

    public boolean handleDeleteTagRequest(ProjectTag projectTag, String userId) {
        //is user an author?
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseThrow(() -> new NoSuchElementException("Author by the userid " + userId + " does not exist"));
        //is user an author of the project?
        AuthorProjectRole authorRole = author.getAuthorProjectRoles().stream()
                .filter(projectRole -> projectRole.getProject().getId().equals(projectTag.getProject().getId()))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException("Author " + author.getAuthorId() + " does not have an AuthorProjectRole with this project " + projectTag.getProject().getId()));
        if (authorRole.getRole() != AuthorProjectRoleType.CREATOR && authorRole.getRole() != AuthorProjectRoleType.MODERATOR) {
            throw new UnauthorizedException("Author " + author.getAuthorId() + " does not have permission to post a tag");
        }
        projectTagRepository.delete(projectTag);
        return true;
    }
}
