package com.user.services.permissions;

import com.common.models.dtos.GroupRole;
import com.common.models.exceptions.UnauthorizedException;
import org.springframework.stereotype.Component;

@Component
public class GroupPermissionsEngine {

    public boolean canUserRolePatchGroup(GroupRole groupRole) {
        if (groupRole == GroupRole.LEADER) {
            return true;
        }
        throw new UnauthorizedException("Only LEADERS can change the group information.");
    }

    public boolean canUserRoleAddRoleType(GroupRole addedByRole, GroupRole requestToAdd) {
        if (addedByRole == GroupRole.BARRED) {
            throw new UnauthorizedException("You're barred and can't add any role.");
        }
        if (addedByRole == GroupRole.MEMBER) {
            throw new UnauthorizedException("You're only a member, and cannot and any new roles");
        }
        if (addedByRole == GroupRole.MODERATORS) {
            if (requestToAdd == GroupRole.LEADER) {
                throw new UnauthorizedException("Nobody can add leaders yet.");
            }
            if (requestToAdd == GroupRole.MODERATORS) {
                throw new UnauthorizedException("Only LEADERS can add new moderators");
            }
            return true;
        }
        if (addedByRole == GroupRole.LEADER) {
            if (requestToAdd == GroupRole.LEADER) {
                throw new UnauthorizedException("Nobody can add leaders yet.");
            }
            return true;
        }
        return false;
    }

    public boolean canUserRoleAddProject(GroupRole role) {
        if (role != GroupRole.LEADER && role != GroupRole.MODERATORS) {
            throw new UnauthorizedException("Only LEADERS or MODERATORS can add projects to a group");
        }
        return true;
    }
}
