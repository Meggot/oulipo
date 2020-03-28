import { Action } from '@ngrx/store';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { AuditModel } from '../../shared/models/audit.model';

export const SET_AUDIT_PAGE = "SET_AUDIT_PAGE"
export const SET_AUDIT_PAGINATION_LINKS = "SET_AUDIT_PAGINATION_LINKS"
export const SET_AUDIT_PAGINATION_DATA = "SET_AUDIT_PAGINATION_DATA"
export const NAVIGATE_AUDIT_PAGE = "NAVIGATE_AUDIT_PAGE"
export const AUDIT_LOAD_FAILED = "AUDIT_LOAD_FAILED"
export const SEARCH_AUDITS = "SEARCH_AUDITS"

export class SearchAudits implements Action {
    readonly type = SEARCH_AUDITS;

    constructor() {}
}

export class SetAuditPage implements Action {
    readonly type = SET_AUDIT_PAGE;

    constructor(public payload: AuditModel[]) {}
}

export class SetAuditPaginationLinks implements Action {
    readonly type = SET_AUDIT_PAGINATION_LINKS;

    constructor(public payload: PaginationLinks) {}
}

export class SetAuditPaginationData implements Action {
    readonly type = SET_AUDIT_PAGINATION_DATA;
    
    constructor(public payload: PaginationData) {}
}

export class NavigateAuditPage implements Action {
    readonly type = NAVIGATE_AUDIT_PAGE;

    constructor(public payload: string) {}
}

export class AuditLoadFailed implements Action {
    readonly type = AUDIT_LOAD_FAILED;

    constructor() {}
}

export type AdminActions =
SetAuditPage |
SetAuditPaginationLinks |
SetAuditPaginationData |
NavigateAuditPage |
AuditLoadFailed |
SearchAudits;