import { AuditModel } from '../../shared/models/audit.model';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import * as AdminActions from './admin.actions';

export interface State {
    auditPage: AuditModel[],
    auditPageLinks: PaginationLinks,
    auditPageData: PaginationData
}

const initialState: State = {
    auditPage: [],
    auditPageLinks: new PaginationLinks({ href: '' }, { href: '' }, { href: '' }, { href: '' }),
    auditPageData: new PaginationData(0, 0, 0, 0)
}

export function adminReducer(state = initialState, action: AdminActions.AdminActions) {
    switch (action.type) {
        case AdminActions.SET_AUDIT_PAGE:
            return {
                ...state,
                auditPage: action.payload
            }
        case AdminActions.SET_AUDIT_PAGINATION_LINKS:
            return {
                ...state,
                auditPageLinks: action.payload
            }
        case AdminActions.SET_AUDIT_PAGINATION_DATA:
            return {
                ...state,
                auditPageData: action.payload
            }
        default:
            return {
                ...state
            }
    }
}