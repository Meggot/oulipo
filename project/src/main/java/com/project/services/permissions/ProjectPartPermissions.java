package com.project.services.permissions;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.dtos.SourcingType;
import com.common.models.exceptions.UnauthorizedException;
import org.springframework.stereotype.Component;

@Component
public class ProjectPartPermissions {

    public boolean canUserRoleRequestPartOnProjectType(AuthorProjectRoleType authorProjectRoleType, SourcingType sourcingType) {
        if (authorProjectRoleType == AuthorProjectRoleType.BARRED) {
            throw new UnauthorizedException("Author is barred from contributing to this project");
        }
        switch (sourcingType) {
            case ARCHIVED:
                throw new UnauthorizedException("Nobody can contribute to Archived projects");
            case OPEN:
                return true;
            case PRIVATE:
                if (authorProjectRoleType == null) {
                    throw new UnauthorizedException("You must be invited to contribute to this private project");
                }
                return true;
            case MODERATED:
                if (authorProjectRoleType == AuthorProjectRoleType.MODERATOR || authorProjectRoleType == AuthorProjectRoleType.CREATOR) {
                    return true;
                } else {
                    throw new UnauthorizedException("Only owners or moderators can contribute further to this project.");
                }
            default:
                throw new RuntimeException("Sourcing Type " + sourcingType + " not recognized in part permissions engine");
        }
    }

    public boolean canUserRoleRequestDeleteOnPart(AuthorProjectRoleType requesterType) {
        if (requesterType == AuthorProjectRoleType.BARRED) {
            throw new UnauthorizedException("Author is barred from editing this project");
        }
        switch (requesterType) {
            case MODERATOR:
            case CREATOR:
                return true;
            default:
                return false;
        }
    }
}

