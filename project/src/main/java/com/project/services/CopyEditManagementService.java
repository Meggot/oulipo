package com.project.services;

import com.common.models.enums.AuthorProjectRoleType;
import com.common.models.enums.CopyEditStatus;
import com.common.models.enums.EditActionType;
import com.common.models.enums.SourcingType;
import com.common.models.exceptions.UnauthorizedException;
import com.common.models.requests.CopyEditRequest;
import com.project.dao.entites.Author;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.entites.Copy;
import com.project.dao.entites.CopyEdit;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.CopyEditRepository;
import com.project.services.permissions.CopyEditPermissions;
import com.project.streaming.ProjectLifecycleStreamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class CopyEditManagementService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CopyEditRepository copyEditRepository;

    @Autowired
    private CopyEditPermissions copyEditPermissions;

    @Autowired
    private CopyManagementService copyManagementService;

    @Autowired
    private ProjectLifecycleStreamer projectLifecycleStreamer;

    public CopyEdit actionAgainstCopyEdit(EditActionType action, String userId, CopyEdit copyEdit) {
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseThrow(() -> new NoSuchElementException("An Author does not exist for a post copy action"));
        SourcingType sourcingType = copyEdit.getCopy().getProject().getSourcingType();
        Integer projectId = copyEdit.getCopy().getProject().getId();
        AuthorProjectRole authorProjectRole = author.getAuthorProjectRoles()
                .stream()
                .filter(role -> role.getProject().getId().equals(projectId))
                .findFirst().orElseThrow(() -> new RuntimeException("An author with userId " + userId
                        + " tried to action a CopyEdit for project " + projectId + " but they do not have a role"));

        if (copyEdit.getStatus() == CopyEditStatus.DENIED) {
            throw new RuntimeException("You cant apply status against denied edits.");
        }

        boolean canAuthorTypePostActionAgainstCopyEdit =
                copyEditPermissions.canAuthorTypePostActionAgainstCopyEdit(authorProjectRole.getRole(), sourcingType);

        if (!canAuthorTypePostActionAgainstCopyEdit) {
            throw new UnauthorizedException("You don't have permission to post actions against this edit");
        }

        switch (action) {
            case REVERT:
                copyEdit.setStatus(CopyEditStatus.REVERTED);
                copyManagementService.revertDeltaToCopy(copyEdit.getDelta(), copyEdit.getCopy());
                break;
            case APPROVE:
                copyEdit.setStatus(CopyEditStatus.APPLIED);
                copyManagementService.applyDeltaToCopy(copyEdit.getDelta(), copyEdit.getCopy());
                break;
            case DECLINE:
                copyEdit.setStatus(CopyEditStatus.DENIED);
                break;
            default:
                throw new RuntimeException("Action " + action + " not supported in CopyEditManagementService");
        }

        return copyEditRepository.save(copyEdit);
    }

    public CopyEdit handlePostCopyEdit(CopyEditRequest copyEditRequest, Copy copy, String userId) {
        Author author = authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseThrow(() -> new NoSuchElementException("An Author does not exist for a post copy edit"));
        SourcingType sourcingType = copy.getProject().getSourcingType();
        Integer projectId = copy.getProject().getId();
        AuthorProjectRole authorProjectRole = author.getAuthorProjectRoles()
                .stream()
                .filter(role -> role.getProject().getId().equals(projectId))
                .findFirst().orElseThrow(() -> new RuntimeException("An author with userId " + userId
                        + " tried to post a new CopyEdit for project " + projectId + " but they do not have a role"));

        boolean canAuthorRolePostCopyEditOnProjectType =
                copyEditPermissions.canAuthorRolePostCopyEditOnProjectType(authorProjectRole.getRole(), sourcingType);

        if (!canAuthorRolePostCopyEditOnProjectType) {
            //Shouldn't ever get here..
            throw new UnauthorizedException("You can't do that");
        }

        CopyEdit newCopyEdit = new CopyEdit();
        copy.addCopyEdit(newCopyEdit);
        author.addCopyEdit(newCopyEdit);
        newCopyEdit.setDelta(copyEditRequest.getDelta());
        newCopyEdit.setStatus(CopyEditStatus.SUBMITTED);
        newCopyEdit = copyEditRepository.save(newCopyEdit);

        if (authorProjectRole.getRole() == AuthorProjectRoleType.CREATOR) {
            actionAgainstCopyEdit(EditActionType.APPROVE, userId, newCopyEdit);
        }

        return newCopyEdit;
    }
}
