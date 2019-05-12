package com.project.services;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.dtos.PartStatus;
import com.common.models.exceptions.PartNotEditableException;
import com.common.models.exceptions.UnauthorizedException;
import com.common.models.messages.ProjectPartCreationMessage;
import com.common.models.messages.ProjectPartUpdateMessage;
import com.common.models.requests.PostPartValueRequest;
import com.project.dao.entites.Author;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectPart;
import com.project.dao.repository.AuthorProjectRoleRepository;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.PartRepository;
import com.project.dao.repository.ProjectRepository;
import com.project.services.permissions.ProjectPartPermissions;
import com.project.streaming.ProjectLifecycleStreamer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

import static com.common.models.dtos.PartStatus.IN_PROGRESS;
import static com.common.models.dtos.PartStatus.RESERVED;

@Component
@Slf4j
public class PartManagementService {

    @Autowired
    PartRepository partRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectPartPermissions projectPartPermissions;

    @Autowired
    CopyManagementService copyManagementService;

    @Autowired
    ProjectLifecycleStreamer projectLifecycleStreamer;

    @Autowired
    AuthorManagementService authorManagementService;

    @Autowired
    AuthorProjectRoleRepository authorProjectRoleRepository;

    public ProjectPart requestPartOnProject(Project project, String userId) {
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseGet(() -> authorManagementService.createAuthor(Integer.parseInt(userId), "Author" + userId));
        Optional<ProjectPart> partOwnedByUserId = project.getPartList().stream().filter(part ->
                part.getStatus().equals(PartStatus.RESERVED)
                || part.getStatus().equals(IN_PROGRESS)
                && part.getCurrentlyHoldingAuthor().getUserId().equals(Integer.parseInt(userId)))
                .findAny();

        if (partOwnedByUserId.isPresent()) {
            return partOwnedByUserId.get();
        }

        Optional<AuthorProjectRole> authorProjectRoleOptional = author.getAuthorProjectRoles().stream()
                .filter(authorProjectRole -> authorProjectRole.getProject().getId().equals(project.getId())).findFirst();
        // As if they have no role, but the project is open - we should run it through permissions engine
        AuthorProjectRoleType authorProjectRoleType = null;
        if (authorProjectRoleOptional.isPresent()) {
            authorProjectRoleType = authorProjectRoleOptional.get().getRole();
        }
        projectPartPermissions.canUserRoleRequestPartOnProjectType(authorProjectRoleType, project.getSourcingType());

        if (authorProjectRoleType == null) {
            AuthorProjectRole authorProjectRole = new AuthorProjectRole();
            project.addAuthorProjectRole(authorProjectRole);
            author.addAuthorProjectRole(authorProjectRole);
            authorProjectRole.setRole(AuthorProjectRoleType.CONTRIBUTOR);
            authorProjectRoleRepository.save(authorProjectRole);
        }

        Optional<ProjectPart> lastCreatedPartOnProject = project.getLastAddedPart();
        ProjectPart newPart = new ProjectPart();
        if (lastCreatedPartOnProject.isPresent()) {
            newPart.setSequence(new Integer(lastCreatedPartOnProject.get().getSequence() + 1));
            newPart.setStatus(PartStatus.RESERVED);
        } else {
            newPart.setSequence(new Integer(1));
            newPart.setStatus(IN_PROGRESS);
        }

        if (project.getPartList().stream()
                .anyMatch(part -> part.getStatus() == IN_PROGRESS)) {
            newPart.setStatus(RESERVED);
        } else {
            newPart.setStatus(IN_PROGRESS);
        }

        newPart.setValue("");
        author.addCreatedPart(newPart);
        project.addPart(newPart);

        newPart = partRepository.save(newPart);
        ProjectPartCreationMessage projectPartCreationMessage = new ProjectPartCreationMessage();
        projectPartCreationMessage.setPartAuthorName(author.getUsername());
        projectPartCreationMessage.setPartId(newPart.getId());
        projectPartCreationMessage.setProjectTitle(project.getTitle());
        projectPartCreationMessage.setProjectId(newPart.getProject().getId());
        projectLifecycleStreamer.sendProjectPartCreationMessage(projectPartCreationMessage);

        projectRepository.save(project);

        return newPart;
    }

    public ProjectPart postValueOnPart(ProjectPart part, String userId, PostPartValueRequest partValue) {
        if (!part.getCurrentlyHoldingAuthor().getUserId().equals(Integer.parseInt(userId))) {
            throw new UnauthorizedException("You are not the current holder of that part");
        }
        if (part.getStatus().equals(PartStatus.LOCKED)) {
            throw new PartNotEditableException("That part is LOCKED and cannot be edited");
        }
        if (part.getSequence() != part.getProject().getNextToBePostedPartSequence()) {
            throw new PartNotEditableException("That part cannot be submitted as it is not the next in the sequence");
        }

        part.setValue(partValue.getValue());
        if (partValue.getReviewStatus().equals(PartStatus.LOCKED)) {
            part.setStatus(partValue.getReviewStatus());
            copyManagementService.addValueToCopy(part.getProject().getCopy(), partValue.getValue());
            setNextInLine(part.getProject());
        }
        part = partRepository.save(part);

        ProjectPartUpdateMessage projectPartUpdateMessage = new ProjectPartUpdateMessage();
        projectPartUpdateMessage.setPartAuthorName(part.getCurrentlyHoldingAuthor().getUsername());
        projectPartUpdateMessage.setPartId(part.getId());
        projectPartUpdateMessage.setPartStatus(part.getStatus().toString());
        projectPartUpdateMessage.setProjectId(part.getProject().getId());
        projectPartUpdateMessage.setProjectTitle(part.getProject().getTitle());
        projectPartUpdateMessage.setPartValue(part.getValue());
        projectPartUpdateMessage.setPartUserId(userId);
        projectLifecycleStreamer.sendProjectPartUpdateMessage(projectPartUpdateMessage);

        return part;
    }

    private void setNextInLine(Project project) {
        Optional<ProjectPart> nextInLine = project.getNextToBeInLineToWriting();
        if (nextInLine.isPresent()) {
            nextInLine.get().setStatus(IN_PROGRESS);
            log.debug("Setting {} part as in progress as it is next in line.", nextInLine.get());
        }
    }

    public boolean deletePart(ProjectPart part, String userId) {
        if (part.getStatus().equals(PartStatus.LOCKED)) {
            log.debug("User {} has requested to delete part {} but it is locked", userId, part.getId());
            return false;
        }
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseThrow(() -> new NoSuchElementException("An author with that UserId does not exist"));

        if (part.getCurrentlyHoldingAuthor().getUserId().equals(author.getUserId())) {
            log.debug("User {} has requested to delete his own part {} deleting..", userId, part.getId());
            partRepository.delete(part);
            deletePart(part);
            return true;
        }

        AuthorProjectRole requesterRole = author.getAuthorProjectRoles().stream()
                .filter(authorProjectRole -> authorProjectRole.getProject().getId().equals(part.getProject().getId())).findFirst()
                .orElseThrow(() -> new NoSuchElementException(("Author does not have an author role for this project")));
        if (projectPartPermissions.canUserRoleRequestDeleteOnPart(requesterRole.getRole())) {
            log.debug("User {} has requested to part {}, and they have the right permission role of {}", userId, part.getId(), requesterRole);
            deletePart(part);
            return true;
        }
        log.debug("User {} has requested to part {}, but they have do not have the right permission. They are: {}", userId, part.getId(), requesterRole);
        return false;
    }


    void deletePart(ProjectPart part) {
        ProjectPartUpdateMessage projectPartUpdateMessage = new ProjectPartUpdateMessage();
        projectPartUpdateMessage.setPartAuthorName(part.getCurrentlyHoldingAuthor().getUsername());
        projectPartUpdateMessage.setPartId(part.getId());
        projectPartUpdateMessage.setPartStatus("DELETED");
        projectPartUpdateMessage.setProjectId(part.getProject().getId());
        projectPartUpdateMessage.setProjectTitle(part.getProject().getTitle());
        projectPartUpdateMessage.setPartValue(part.getValue());
        projectPartUpdateMessage.setPartUserId(String.valueOf(part.getCurrentlyHoldingAuthor().getUserId()));
        projectLifecycleStreamer.sendProjectPartUpdateMessage(projectPartUpdateMessage);

        Project project = part.getProject();
        partRepository.delete(part);
        setNextInLine(project);
    }
}
