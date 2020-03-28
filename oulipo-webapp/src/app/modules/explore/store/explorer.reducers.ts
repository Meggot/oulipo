import { ProjectModel } from '../../shared/models/project.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import * as ExplorerActions from './explorer.actions'

export interface State {
    activePage: ProjectModel[],
    pageLinks: PaginationLinks,
    pageData: PaginationData
}

const initialState: State = {
    activePage: [],
    pageLinks: new PaginationLinks({ href: '' }, { href: '' }, { href: '' }, { href: '' }),
    pageData: new PaginationData(0, 0, 0, 0)
}

export function explorerReducer(state = initialState, action: ExplorerActions.ExplorerActions) {
    switch (action.type) {
        case ExplorerActions.SET_ACTIVE_PAGE:
            return {
                ...state,
                activePage: action.payload
            }
        case ExplorerActions.SET_PAGINATION_DATA:
            return {
                ...state,
                pageData: action.payload
            }
        case ExplorerActions.SET_PAGINATION_LINKS:
            return {
                ...state,
                pageLinks: action.payload
            }
        default:
            return {
                ...state
            }
    }
}