package com.project.services;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.dtos.PartStatus;
import com.common.models.exceptions.PartNotEditableException;
import com.common.models.exceptions.UnauthorizedException;
import com.common.models.requests.PostPartValueRequest;
import com.github.tomakehurst.wiremock.security.NotAuthorisedException;
import com.project.dao.entites.Author;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectPart;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.PartRepository;
import com.project.dao.repository.ProjectRepository;
import com.project.services.permissions.ProjectPartPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

@Component
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

    public ProjectPart requestPartOnProject(Project project, String userId) {
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseThrow(() -> new NoSuchElementException("Author with UserId " + userId + " was not found "));
        Optional<ProjectPart> partOwnedByUserId = project.getPartList().stream().filter(part -> part.getStatus().equals(PartStatus.RESERVED) && part.getCurrentlyHoldingAuthor().getUserId().equals(Integer.parseInt(userId))).findAny();
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

        Optional<ProjectPart> lastCreatedPartOnProject = project.getLastAddedPart();
        ProjectPart newPart = new ProjectPart();
        if (lastCreatedPartOnProject.isPresent()) {
            newPart.setSequence(new Integer(lastCreatedPartOnProject.get().getSequence() + 1));
        } else {
            newPart.setSequence(new Integer(1));
        }
        newPart.setStatus(PartStatus.RESERVED);
        newPart.setValue("sda");
        author.addCreatedPart(newPart);
        project.addPart(newPart);
        newPart = partRepository.save(newPart);
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
        part.setStatus(partValue.getReviewStatus());
        if (partValue.getReviewStatus().equals(PartStatus.LOCKED)) {
            copyManagementService.addValueToCopy(part.getProject().getCopy(), partValue.getValue());
        }
        part = partRepository.save(part);
        return part;
    }
}
