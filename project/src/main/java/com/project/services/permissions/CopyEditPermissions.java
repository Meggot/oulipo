package com.project.services.permissions;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.dtos.SourcingType;
import com.common.models.exceptions.UnauthorizedException;
import org.springframework.stereotype.Component;

@Component
public class CopyEditPermissions {

    public boolean canAuthorRolePostCopyEditOnProjectType(AuthorProjectRoleType authorProjectRole, SourcingType sourcingType) {
        switch (sourcingType) {
            case ARCHIVED:
                throw new UnauthorizedException("Project is archived and nobody can post edits");
            case MODERATED:
                if (authorProjectRole == AuthorProjectRoleType.MODERATOR || authorProjectRole == AuthorProjectRoleType.CREATOR) {
                    return true;
                }
                throw new UnauthorizedException("Only Moderators or Owners can post edits to moderated projects");
            case PRIVATE:
                if (authorProjectRole == AuthorProjectRoleType.CONTRIBUTOR ||
                        authorProjectRole == AuthorProjectRoleType.MODERATOR ||
                        authorProjectRole == AuthorProjectRoleType.CREATOR) {
                    return true;
                }
                throw new UnauthorizedException("Only Contributors, Owners or Moderators can post to Private projects");
            case OPEN:
                if (authorProjectRole != AuthorProjectRoleType.BARRED) {
                    return true;
                }
                throw new UnauthorizedException("You are Barred from this project and may not post edits");
            default:
                throw new RuntimeException("Sourcing type " + sourcingType + " is not supported in CopyEditPermissions engine.");
        }
    }

    public boolean canAuthorTypePostActionAgainstCopyEdit(AuthorProjectRoleType role, SourcingType sourcingType) {
        switch (sourcingType) {
            case ARCHIVED:
                throw new UnauthorizedException("Project is archived and nobody can post actions against an edit");
            case MODERATED:
                if (role == AuthorProjectRoleType.MODERATOR || role == AuthorProjectRoleType.CREATOR) {
                    return true;
                }
                throw new UnauthorizedException("Only Moderators or Owners can post actions against copy edits in Moderated projects");
            case PRIVATE:
                if (role == AuthorProjectRoleType.CONTRIBUTOR ||
                        role == AuthorProjectRoleType.MODERATOR ||
                        role == AuthorProjectRoleType.CREATOR) {
                    return true;
                }
                throw new UnauthorizedException("Only Contributors, Owners or Moderators can post actions against copy edits in Private projects");
            case OPEN:
                if (role != AuthorProjectRoleType.BARRED) {
                    return true;
                }
                throw new UnauthorizedException("You are Barred from this project and may not post edit actions.");
            default:
                throw new RuntimeException("Sourcing type " + sourcingType + " is not supported in CopyEditPermissions engine.");
        }
    }
}
