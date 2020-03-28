import { Action } from '@ngrx/store'
import { ExplorerSearchQuery } from '../../shared/models/explorer-search-query.model';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { ProjectModel } from '../../shared/models/project.model';

export const SEARCH = "SEARCH"
export const SEARCH_FAILED = "SEARCH_FAILED"
export const SET_PAGINATION_LINKS = "SET_PAGINATION_LINKS"
export const SET_PAGINATION_DATA = "SET_PAGINATION_DATA"
export const SET_ACTIVE_PAGE = "SET_ACTIVE_PAGE"
export const NAVIGATE_PAGE = "NAVIGATE_PAGE"

export class NavigatePage implements Action {
    readonly type = NAVIGATE_PAGE;

    constructor(public pageUrl: string) {}
}

export class SearchFailed implements Action {
    readonly type = SEARCH_FAILED;

    constructor() {}
}

export class Search implements Action {
    readonly type = SEARCH;

    constructor(public payload: ExplorerSearchQuery) {}
}

export class SetPaginationlinks implements Action {
    readonly type = SET_PAGINATION_LINKS;

    constructor(public payload: PaginationLinks) {}
}

export class SetPaginationData implements Action {
    readonly type = SET_PAGINATION_DATA;

    constructor(public payload: PaginationData) {}
}

export class SetActivePage implements Action {
    readonly type = SET_ACTIVE_PAGE;

    constructor(public payload: ProjectModel[]) {}
}

export type ExplorerActions = 
SetPaginationData | 
SetPaginationlinks | 
SetActivePage|
NavigatePage;