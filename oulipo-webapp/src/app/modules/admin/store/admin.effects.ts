import { Injectable } from "@angular/core";
import { Actions, Effect, ofType } from '@ngRx/effects';
import { AdminApi } from './admin.api';

import * as AdminActions from './admin.actions'
import { mergeMap, map, switchMap } from 'rxjs/operators';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import { AuditModel } from '../../shared/models/audit.model';
@Injectable()
export class AdminEffects {

    constructor(private actions$: Actions,
        private adminApi: AdminApi) {}

    @Effect()
    onload = this.actions$.pipe(
        ofType(AdminActions.SEARCH_AUDITS),
        switchMap(() => {
            return this.adminApi.loadAudits();
        }),
        mergeMap((response: ApiResponse<{ pageLinks: PaginationLinks, pageData: PaginationData, page: AuditModel[]}>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: AdminActions.SET_AUDIT_PAGINATION_DATA,
                        payload: response.data.pageData
                    },
                    {
                        type: AdminActions.SET_AUDIT_PAGINATION_LINKS,
                        payload: response.data.pageLinks
                    },
                    {
                        type: AdminActions.SET_AUDIT_PAGE,
                        payload: response.data.page
                    }
                ]
            } else {
                return [
                    {
                        type: AdminActions.AUDIT_LOAD_FAILED
                    }
                ]
            }
        })
    )
    

    @Effect()
    onNavigatePageUrl = this.actions$.pipe(
        ofType(AdminActions.NAVIGATE_AUDIT_PAGE),
        map((action: AdminActions.NavigateAuditPage) => {
            return action.payload;
        }),
        switchMap((payload: string) => {
            return this.adminApi.navigateAuditPageUrl(payload);
        }),
        mergeMap((response: ApiResponse<{ pageLinks: PaginationLinks, pageData: PaginationData, page: AuditModel[]}>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: AdminActions.SET_AUDIT_PAGINATION_DATA,
                        payload: response.data.pageData
                    },
                    {
                        type: AdminActions.SET_AUDIT_PAGINATION_LINKS,
                        payload: response.data.pageLinks
                    },
                    {
                        type: AdminActions.SET_AUDIT_PAGE,
                        payload: response.data.page
                    }
                ]
            } else {
                return [
                    {
                        type: AdminActions.AUDIT_LOAD_FAILED
                    }
                ]
            }
        })
    )
}