import { ProjectModel } from '../../shared/models/project.model';
import { PartModel } from '../../shared/models/part.model';
import * as ProjectActions from './project.actions';
import { AuthorModel } from '../../shared/models/author.model';
import { CopyModel } from '../../shared/models/copy.model';
import { AuditModel } from '../../shared/models/audit.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { SubErroredResponse } from '../../shared/models/subErroredResponse';

export interface State {
    authoredProjects: ProjectModel[],
    authoredParts: PartModel[],
    projects: ProjectModel[],
    authors: AuthorModel[],
    viewingAuthor: AuthorModel,
    viewingProject: ProjectModel
    viewingCopy: CopyModel;
    writingPart: PartModel;
    postRoleErrorMessage: string;
    requestPartErrorMessage: string;
    editErrorMessage: string;
    createProjectFailMessage: SubErroredResponse;
    projectHistoryPage: AuditModel[];
    projectHistoryPaginationData: PaginationData;
    projectHistoryPaginationLinks: PaginationLinks;
    projectHistoryMessage: string;
}

const initialState: State = {
    authoredProjects: [],
    authoredParts: [],
    projects: [],
    authors: [],
    viewingAuthor: null,
    viewingProject: null,
    viewingCopy: null,
    writingPart: null,
    postRoleErrorMessage: '',
    requestPartErrorMessage: '',
    editErrorMessage: '',
    createProjectFailMessage: null,
    projectHistoryPage: [],
    projectHistoryPaginationData: null,
    projectHistoryPaginationLinks: null,
    projectHistoryMessage: ''
}

export function projectReducer(state = initialState, action: ProjectActions.ProjectActions) {
    switch (action.type) {
        case ProjectActions.ADD_ROLE_TO_VIEWING_PROJECT:
            return {
                ...state,
                postRoleErrorMessage: 'Successfully added role.'
            }
        case ProjectActions.ADD_PROJECT:
            return {
                ...state,
                projects: [...state.projects, action.payload]
            }
        case ProjectActions.ADD_PROJECTS:
            return {
                ...state,
                projects: [...state.projects, ...action.payload]
            }
        case ProjectActions.ADD_AUTHORED_PROJECT:
            return {
                ...state,
                authoredProjects: [...state.authoredProjects, action.payload]
            }
        case ProjectActions.ADD_AUTHORED_PROJECTS:
            return {
                ...state,
                authoredProjects: [...state.authoredProjects, ...action.payload]
            }
        case ProjectActions.ADD_AUTHORED_PART:
            return {
                ...state,
                authoredParts: [...state.authoredParts, action.payload]
            }
        case ProjectActions.ADD_AUTHORED_PARTS:
            return {
                ...state,
                authoredParts: [...state.authoredParts, ...action.payload]
            }
        case ProjectActions.ADD_AUTHOR:
            return {
                ...state,
                authors: [...state.authors, action.payload]
            }
        case ProjectActions.SET_VIEWING_AUTHOR:
            return {
                ...state,
                viewingAuthor: action.payload
            }
        case ProjectActions.SET_VIEWING_PROJECT:
            return {
                ...state,
                viewingProject: action.payload,
                requestPartErrorMessage: ''
            }
        case ProjectActions.CREATE_PROJECT_FAIL:
            return {
                ...state,
                createProjectFailMessage: action.payload
            }
        case ProjectActions.SET_VIEWING_COPY:
            return {
                ...state,
                viewingCopy: action.payload
            }
        case ProjectActions.SET_WRITING_PART:
            return {
                ...state,
                writingPart: action.payload
            }
        case ProjectActions.LOGOUT:
            return {
                ...state = initialState
            }
        case ProjectActions.REQUEST_PART_FAILED:
            return {
                ...state,
                requestPartErrorMessage: action.payload
            }
        case ProjectActions.POST_ROLE_ON_PROJECT_FAIL:
            return {
                ...state,
                postRoleErrorMessage: action.payload
            }
        case ProjectActions.SET_EDIT_MESSAGE:
            return {
                ...state,
                editErrorMessage: action.payload
            }
        case ProjectActions.SET_PROJECT_HISTORY_PAGE_LINKS:
            return {
                ...state,
                projectHistoryPaginationLinks: action.payload
            }
        case ProjectActions.SET_PROJECT_HISTORY_MESSAGE:
            return {
                ...state,
                projectHistoryMessage: action.payload
            }
        case ProjectActions.SET_PROJECT_HISTORY_PAGE_DATA:
            return {
                ...state,
                projectHistoryPaginationData: action.payload
            }
        case ProjectActions.SET_PROJECT_HISTORY_PAGE:
            return {
                ...state,
                projectHistoryPage: action.payload
            }
        default:
            return state;
    }
}