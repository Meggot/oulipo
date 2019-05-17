package com.project.services;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.dtos.TagType;
import com.common.models.exceptions.UnauthorizedException;
import com.common.models.messages.ProjectTagCreationMessage;
import com.common.models.messages.ProjectTagUpdateMessage;
import com.common.models.requests.CreateTagRequest;
import com.project.dao.entites.Author;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectTag;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.ProjectRepository;
import com.project.dao.repository.ProjectTagRepository;
import com.project.streaming.ProjectLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Int;

import java.util.NoSuchElementException;

@Component
@Slf4j
public class ProjectTagManagementService {

    private AuthorRepository authorRepository;

    private ProjectTagRepository projectTagRepository;

    private ProjectRepository projectRepository;

    @Autowired
    ProjectTagManagementService(AuthorRepository authorRepository,
                                ProjectTagRepository projectTagRepository,
                                ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.authorRepository = authorRepository;
        this.projectTagRepository = projectTagRepository;
    }

    public ProjectTag handleCreateTagRequest(CreateTagRequest createTagRequest, Project project, String userId) {
        //is user an author?
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseThrow(() -> new NoSuchElementException("Author by the userid " + userId + " does not exist"));
        //is user an author of the project?
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
            throw new UnauthorizedException("Author " + author.getAuthorId() + " does not have permission to delete a tag");
        }

        projectTagRepository.delete(projectTag);
        return true;
    }

    public void handleSystemAddTag(CreateTagRequest body, Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Received a system add tag for a project that doesn't exist of id " + projectId));
        ProjectTag newTag = new ProjectTag();
        newTag.setType(TagType.SYSTEM_ADDED);
        newTag.setValue(body.getValue());
        project.addTag(newTag);
        newTag = projectTagRepository.save(newTag);
        log.info("Added SYSTEM_TAG of {} ", newTag);
    }
}
