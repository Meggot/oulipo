package com.project.services.permissions;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.exceptions.UnauthorizedException;
import org.springframework.stereotype.Component;


@Component
public class AuthorRolePermissions {

    /**
     * Returns TRUE or UnauthorizedException (with error)
     */
    public boolean canUserRolePostRole(AuthorProjectRoleType actorRole, AuthorProjectRoleType actedRole) throws UnauthorizedException {
        switch (actedRole) {
            case CREATOR:
                throw new UnauthorizedException("Nobody can set a new owner except for the system.");
            case CONTRIBUTOR:
                if (actorRole == AuthorProjectRoleType.CREATOR || actorRole == AuthorProjectRoleType.MODERATOR) {
                    return true;
                } else {
                    throw new UnauthorizedException("Only CREATORS or MODERATORS can invite authors to contribute");
                }
            case MODERATOR:
                if (actorRole == AuthorProjectRoleType.CREATOR) {
                    return true;
                } else {
                    throw new UnauthorizedException("Only CREATORS can set new MODERATORS");
                }
            case BARRED:
                if (actorRole == AuthorProjectRoleType.MODERATOR || actorRole == AuthorProjectRoleType.CREATOR) {
                    return true;
                } else {
                    throw new UnauthorizedException("Only CREATORS or MODERATORS can bar authors.");
                }
            default:
                throw new RuntimeException("Type: " + actedRole + " not supported by permissions engine");
        }
    }

    public boolean canUserRolePatchRole(AuthorProjectRoleType actorRole, AuthorProjectRoleType newActedRole, AuthorProjectRoleType previousActedRole) throws UnauthorizedException {
        if (previousActedRole == AuthorProjectRoleType.CREATOR) {
            throw new UnauthorizedException("Nobody can update an owner to any role.");
        }
        switch (newActedRole) {
            case CREATOR:
                throw new UnauthorizedException("Nobody can update a role to owner");
            case MODERATOR:
                if (actorRole == AuthorProjectRoleType.CREATOR) {
                    return true;
                } else {
                    throw new UnauthorizedException("Only CREATORS can update to moderators");
                }
            case CONTRIBUTOR:
                if (actorRole == AuthorProjectRoleType.CREATOR || (actorRole == AuthorProjectRoleType.MODERATOR &&
                        previousActedRole != AuthorProjectRoleType.MODERATOR)) {
                    return true;
                } else {
                    throw new UnauthorizedException("Only CREATORS or MODERATORS can update roles to contributor, and only owners can demote.");
                }
            case BARRED:
                if (actorRole == AuthorProjectRoleType.CREATOR || (actorRole == AuthorProjectRoleType.MODERATOR &&
                        previousActedRole != AuthorProjectRoleType.MODERATOR)) {
                    return true;
                } else {
                    throw new UnauthorizedException("Only CREATORS or MODERATORS can bar authors, and only owners can bar moderators.");
                }
            default:
                throw new RuntimeException("Type: " + newActedRole + " not supported by permissions engine");
        }
    }
}
