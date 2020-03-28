import { Action } from '@ngrx/store'
import { ProjectModel } from '../../shared/models/project.model';
import { PartModel } from '../../shared/models/part.model';
import { AuthorModel } from '../../shared/models/author.model';
import { CopyModel } from '../../shared/models/copy.model';
import { SetThisAccount } from '../../accounts/store/account.actions';
import { CreateProjectModel } from '../../shared/models/create-project.model';
import { ProjectRolesModel } from '../../shared/models/project-roles.model';
import { AuditModel } from '../../shared/models/audit.model';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { SubErroredResponse } from '../../shared/models/subErroredResponse';

export const LOAD = "PROJECT_LOAD"
export const REFRESH_VIEWING_PROJECT = "REFRESH_VIEWING_PROJECT"
export const FIND_AUTHOR_BY_ID = "FIND_AUTHOR_BY_ID"
export const LOAD_THIS_AUTHOR = "LOAD_THIS_AUTHOR"
export const LOAD_PROJECTS = "LOAD_PROJECTS"
export const LOAD_FAILED = "LOAD_FAILED"
export const ADD_PROJECT = "ADD_PROJECT"
export const ADD_PROJECTS = "ADD_PROJECTS"
export const CREATE_PROJECT = "CREATE_PROJECT"
export const ADD_AUTHORED_PROJECT = "ADD_AUTHORED_PROJECT"
export const ADD_AUTHORED_PROJECTS = "ADD_AUTHORED_PROJECTS"
export const ADD_AUTHORED_PART = "ADD_AUTHORED_PART"
export const ADD_AUTHORED_PARTS = "ADD_AUTHORED_PARTS"
export const ADD_AUTHOR = "ADD_AUTHOR"
export const SET_VIEWING_AUTHOR = "SET_VIEWING_AUTHOR"
export const FIND_AUTHOR_FAILED = "FIND_AUTHOR_FAILED"
export const LOAD_AND_SET_VIEWING_AUTHOR_BY_ID = "LOAD_AND_SET_VIEWING_AUTHOR_BY_ID"
export const LOAD_AND_SET_VIEWING_PROJECT_BY_ID = "LOAD_AND_SET_VIEWING_PROJECT_BY_ID"
export const SET_VIEWING_PROJECT = "SET_VIEWING_PROJECT"
export const SET_VIEWING_COPY = "SET_VIEWING_COPY"
export const REQUEST_PART_ON_PROJECT_ID = "REQUEST_PART_ON_PROJECT_ID"
export const POST_PART_VALUE_AND_REVIEW_STATUS_ON_PART_ID = "POST_PART_VALUE_AND_REVIEW_STATUS_ON_PART_ID"
export const REQUEST_PART_FAILED = "REQUEST_PART_FAILED"
export const CREATE_PROJECT_FAIL = "CREATE_PROJECT_FAIL"
export const SET_WRITING_PART = "SET_WRITING_PART"
export const SET_THIS_AUTHOR_WRITING = "SET_THIS_AUTHOR_WRITING"
export const LOGOUT = "PROJECTS-LOGOUT"
export const SUBMIT_EDIT = "SUBMIT_EDIT"
export const SET_EDIT_MESSAGE = "SET_EDIT_MESSAGE"
export const ACTION_ON_EDIT = "ACTION_ON_EDIT"
export const POST_TAG_VALUE_ON_PROJECT = "POST_TAG_VALUE_ON_PROJECT"
export const POST_ROLE_ON_PROJECT = "POST_ROLE_ON_PROJECT"
export const POST_ROLE_ON_PROJECT_FAIL = "POST_ROLE_ON_PROJECT_FAIL"
export const PATCH_ROLE_ON_PROJECT = "PATCH_ROLE_ON_PROJECT"
export const ADD_ROLE_TO_VIEWING_PROJECT = "ADD_ROLE_TO_VIEWING_PROJECT"
export const DELETE_PART = "DELETE_PART"
//---HISTORY
export const RETRIEVE_PROJECT_HISTORY = "RETRIEVE_PROJECT_HISTORY"
export const SET_PROJECT_HISTORY_MESSAGE = "SET_PROJECT_HISTORY_MESSAGE"
export const SET_PROJECT_HISTORY_PAGE = "SET_PROJECT_HISTORY_PAGE"
export const SET_PROJECT_HISTORY_PAGE_LINKS = "SET_PROJECT_HISTORY_PAGE_LINKS"
export const SET_PROJECT_HISTORY_PAGE_DATA = "SET_PROJECT_HISTORY_PAGE_DATA"


export class RetrieveProjectHistory implements Action {
    readonly type = RETRIEVE_PROJECT_HISTORY;

    constructor(public payload: number) {}
}

export class SetProjectHistoryPageLinks implements Action {
    readonly type = SET_PROJECT_HISTORY_PAGE_LINKS;

    constructor(public payload: PaginationLinks) {}
}

export class SetProjectHistoryPageData implements Action {
    readonly type = SET_PROJECT_HISTORY_PAGE_DATA;

    constructor(public payload: PaginationData) {}
}

export class SetProjectHistoryPage implements Action {
    readonly type = SET_PROJECT_HISTORY_PAGE;

    constructor(public payload: AuditModel[]) {}
}

export class SetProjectHistoryMessage implements Action {
    readonly type = SET_PROJECT_HISTORY_MESSAGE;

    constructor(public payload: string) {}
}

//---

export class RefreshViewingProject implements Action {
    readonly type = REFRESH_VIEWING_PROJECT;

    constructor() {}
}

export class ActionOnEdit implements Action {
    readonly type = ACTION_ON_EDIT;
    
    constructor(public payload: {editId: number, action: string}) {}
}

export class DeletePart implements Action {
    readonly type = DELETE_PART;

    constructor(public payload: number) {}
}
export class PatchRoleOnProject implements Action {
    readonly type = PATCH_ROLE_ON_PROJECT;

    constructor(public payload: {roleId: number, newRole: string}) {}
}

export class AddRoleToViewingProject implements Action {
    readonly type = ADD_ROLE_TO_VIEWING_PROJECT;

    constructor(public payload: ProjectRolesModel) {}
}

export class PostRoleOnProject implements Action {
    readonly type = POST_ROLE_ON_PROJECT;

    constructor(public payload: {projectId: number, userId: number, role: string}) {}
}
export class PostRoleOnProjectFail implements Action {
    readonly type = POST_ROLE_ON_PROJECT_FAIL;

    constructor(public payload: string) {}
}

export class PostTagValueOnProject implements Action {
    readonly type = POST_TAG_VALUE_ON_PROJECT;

    constructor(public payload: {projectId: number, value: string}) {}
}

export class SubmitEdit implements Action {
    readonly type = SUBMIT_EDIT;

    constructor(public payload: {copyId: number, deltaString: string}) {}
}

export class SetEditMessage implements Action {
    readonly type = SET_EDIT_MESSAGE;

    constructor(public payload: string) {}
}

export class Logout implements Action {
    readonly type = LOGOUT;

    constructor() {}
}

export class CreateProjectFail implements Action {
    readonly type = CREATE_PROJECT_FAIL;

    constructor(public payload: SubErroredResponse) {}
}

export class CreateProject implements Action {
    readonly type = CREATE_PROJECT;

    constructor(public payload: CreateProjectModel) {}
}

export class SetThisAuthorWriting implements Action {
    readonly type = SET_THIS_AUTHOR_WRITING

    constructor (public payload: boolean) {}
}

export class SetWritingPart implements Action {
    readonly type = SET_WRITING_PART;

    constructor(public payload: PartModel) {}
}
export class RequestPartFailed implements Action {
    readonly type = REQUEST_PART_FAILED;

    constructor(public payload: string) {} 
}

export class RequestPartOnProjectId implements Action {
    readonly type = REQUEST_PART_ON_PROJECT_ID

    constructor(public payload: number) {}
}

export class PostPartValueAndReviewStatusOnPartId implements Action {
    readonly type = POST_PART_VALUE_AND_REVIEW_STATUS_ON_PART_ID

    constructor(public payload: {partId: number, value: string, reviewStatus: string}) {}
}

export class Load implements Action {
    readonly type = LOAD;
}

export class SetViewingCopy implements Action {
    readonly type = SET_VIEWING_COPY;

    constructor(public payload: CopyModel) {}
}

export class LoadAndSetViewingAuthorById implements Action {
    readonly type = LOAD_AND_SET_VIEWING_AUTHOR_BY_ID;

    constructor(public payload: number) {}
}

export class FindAuthorFailed implements Action {
    readonly type = FIND_AUTHOR_FAILED;

    constructor() {}
}

export class SetViewingAuthor implements Action {
    readonly type = SET_VIEWING_AUTHOR;

    constructor(public payload: AuthorModel) {}
}

export class FinddAuthorById implements Action {
    readonly type = FIND_AUTHOR_BY_ID;

    constructor(public payload: number) {}
}

export class LoadThisAuthor implements Action {
    readonly type = LOAD_THIS_AUTHOR;

    constructor() {}
}

export class AddAuthor implements Action {
    readonly type = ADD_AUTHOR;

    constructor(public payload: AuthorModel) {}
}

export class AddProject implements Action {
    readonly type = ADD_PROJECT;
    
    constructor(public payload: ProjectModel) {}
}

export class AddProjects implements Action {
    readonly type = ADD_PROJECTS;
    
    constructor(public payload: ProjectModel[]) {}
}

export class AddAuthoredProject implements Action {
    readonly type = ADD_AUTHORED_PROJECT;

    constructor(public payload: ProjectModel) {}
}

export class AddAuthoredProjects implements Action {
    readonly type = ADD_AUTHORED_PROJECTS;

    constructor(public payload: ProjectModel[]) {}
}

export class AddAuthoredPart implements Action {
    readonly type = ADD_AUTHORED_PART;

    constructor(public payload: PartModel) {}
}

export class AddAuthoredParts implements Action {
    readonly type = ADD_AUTHORED_PARTS;

    constructor(public payload: PartModel[]) {}
}

export class LoadFailed implements Action {
    readonly type = LOAD_FAILED;

    constructor() {}
}

export class ProjectLoad implements Action {
    readonly type = LOAD_PROJECTS;

    constructor() {}
}

export class SetViewingProject implements Action {
    readonly type = SET_VIEWING_PROJECT;

    constructor(public payload: ProjectModel) {}
}

export class LoadandSetViewingProjectById implements Action {
    readonly type = LOAD_AND_SET_VIEWING_PROJECT_BY_ID;

    constructor(public payload: number) {}
}

export type ProjectActions =
AddProject | 
RefreshViewingProject | 
AddProjects | 
AddAuthoredProject | 
AddAuthoredProjects | 
AddAuthoredPart | 
AddAuthoredParts | 
LoadFailed | 
AddAuthor | 
SetViewingAuthor |
LoadAndSetViewingAuthorById |
SetViewingProject |
LoadandSetViewingProjectById |
SetViewingCopy |
RequestPartOnProjectId |
RequestPartFailed |
PostPartValueAndReviewStatusOnPartId |
SetThisAuthorWriting |
SetWritingPart |
SetThisAccount |
CreateProject |
CreateProjectFail |
Logout |
SubmitEdit |
SetEditMessage |
PostRoleOnProject |
PostRoleOnProjectFail |
AddRoleToViewingProject |
DeletePart |
ActionOnEdit |
RetrieveProjectHistory |
SetProjectHistoryMessage |
SetProjectHistoryPage |
SetProjectHistoryPageData |
SetProjectHistoryPageLinks;