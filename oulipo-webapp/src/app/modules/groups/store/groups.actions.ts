import { GroupModel } from '../../shared/models/group.model';
import { Action } from '@ngrx/store';

export const GROUPS_LOAD = "GROUPS_LOAD"
export const GROUP_LOAD_ERROR = "GROUP_LOAD_ERROR"

export const ADD_GROUP = "ADD_GROUP"
export const ADD_GROUPS = "ADD_GROUPS"
export const SET_VIEWING_GROUP = "SET_VIEWING_GROUP"
export const GROUPS_LOGOUT = "GROUPS_LOGOUT"
export const LOAD_AND_SET_VIEWING_GROUP = "LOAD_AND_SET_VIEWING_GROUP"
export const SET_GROUP_SEARCH_RESULT = "SET_GROUP_SEARCH_RESULT"
export const SEARCH_GROUPS = "SEARCH_GROUPS"

export const POST_GROUP_MEMBERSHIP = "POST_GROUP_MEMBERSHIP"

export class PostGroupMembership implements Action {
    readonly type = POST_GROUP_MEMBERSHIP;

    constructor(public payload: {groupId: number, username: string, role: string}) {}
}

export class SearchGroups implements Action {
    readonly type = SEARCH_GROUPS;

    constructor(public payload: string) {}
}

export class SetGroupSearchResult implements Action {
    readonly type = SET_GROUP_SEARCH_RESULT;

    constructor(public payload: GroupModel[]) {}
}

export class LoadAndSetViewingGroup implements Action {
    readonly type = LOAD_AND_SET_VIEWING_GROUP;

    constructor(public payload: number) {}
}

export class GroupsLoad implements Action {
    readonly type = GROUPS_LOAD;

    constructor() {}
}

export class GroupsLoadError implements Action {
    readonly type = GROUP_LOAD_ERROR;

    constructor(public payload: string) {}
}

export class AddGroup implements Action {
    readonly type = ADD_GROUP;

    constructor(public payload: GroupModel) {}
}

export class AddGroups implements Action {
    readonly type = ADD_GROUPS;

    constructor(public payload: GroupModel[]) {}
}

export class SetViewingGroup implements Action {
    readonly type = SET_VIEWING_GROUP;

    constructor(public payload: GroupModel) {}
}

export class GroupLogout implements Action {
    readonly type = GROUPS_LOGOUT;

    constructor() {}
}

export type GroupActions =
GroupsLoad |
GroupsLoadError |
AddGroup |
AddGroups |
SetViewingGroup |
GroupLogout |
LoadAndSetViewingGroup |
SearchGroups |
SetGroupSearchResult |
PostGroupMembership;