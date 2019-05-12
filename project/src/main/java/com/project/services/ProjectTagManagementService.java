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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class ProjectTagManagementService {

    private AuthorRepository authorRepository;

    private ProjectTagRepository projectTagRepository;

    private ProjectLifecycleStreamer projectLifecycleStreamer;

    @Autowired
    ProjectTagManagementService(AuthorRepository authorRepository,
                                ProjectTagRepository projectTagRepository,
                                ProjectLifecycleStreamer projectLifecycleStreamer) {
        this.authorRepository = authorRepository;
        this.projectTagRepository = projectTagRepository;
        this.projectLifecycleStreamer = projectLifecycleStreamer;
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

        ProjectTagCreationMessage projectTagCreationMessage = new ProjectTagCreationMessage();
        projectTagCreationMessage.setProjectTitle(project.getTitle());
        projectTagCreationMessage.setProjectId(project.getId());
        projectTagCreationMessage.setTagId(newTag.getId());
        projectTagCreationMessage.setType(newTag.getType());
        projectTagCreationMessage.setUserIdAdded(author.getUserId());
        projectTagCreationMessage.setValue(newTag.getValue());
        projectLifecycleStreamer.sendProjectTagCreationMessage(projectTagCreationMessage);

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

        ProjectTagUpdateMessage projectTagUpdateMessage = new ProjectTagUpdateMessage();
        projectTagUpdateMessage.setValue("DELETED");
        projectTagUpdateMessage.setProjectTitle(projectTag.getProject().getTitle());
        projectTagUpdateMessage.setProjectId(projectTag.getProject().getId());
        projectTagUpdateMessage.setTagId(projectTag.getId());
        projectTagUpdateMessage.setType(projectTag.getType());
        projectTagUpdateMessage.setUserIdAdded(author.getUserId());
        projectLifecycleStreamer.sendProjectTagUpdateMessage(projectTagUpdateMessage);

        projectTagRepository.delete(projectTag);
        return true;
    }
}
