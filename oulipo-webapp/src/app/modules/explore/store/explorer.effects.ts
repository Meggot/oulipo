import { Injectable } from "@angular/core";
import { Actions, Effect, ofType } from '@ngRx/effects';
import { ExplorerApi } from './explorer.api';

import * as ExplorerActions from './explorer.actions';
import { map, switchMap, mergeMap } from 'rxjs/operators';
import { ExplorerSearchQuery } from '../../shared/models/explorer-search-query.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import { ExplorerSearchResults } from '../../shared/models/explorer-search-results.model';

@Injectable()
export class ExplorerEffects {

    constructor(private actions$: Actions,
        private explorerApi: ExplorerApi) {}

    @Effect()
    navigatePage = this.actions$.pipe(
        ofType(ExplorerActions.NAVIGATE_PAGE),
        map((action: ExplorerActions.NavigatePage) => {
            return action.pageUrl;
        }),
        switchMap((pageUrl: string) => {
            return this.explorerApi.navigatePageUrl(pageUrl);
        }),
        mergeMap((response: ApiResponse<ExplorerSearchResults>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ExplorerActions.SET_ACTIVE_PAGE,
                        payload: response.data.page
                    },
                    {
                        type: ExplorerActions.SET_PAGINATION_DATA,
                        payload: response.data.pageData
                    },
                    {
                        type: ExplorerActions.SET_PAGINATION_LINKS,
                        payload: response.data.pageLinks
                    }
                ]
            } else {
                return [
                    {
                        type: ExplorerActions.SEARCH_FAILED
                    }
                ]
            }
        })
    )
    
    @Effect()
    search = this.actions$.pipe(
        ofType(ExplorerActions.SEARCH),
        map((action: ExplorerActions.Search) => {
            return action.payload;
        }),
        switchMap((payload: ExplorerSearchQuery) => {
            return this.explorerApi.search(payload);
        }),
        mergeMap((response: ApiResponse<ExplorerSearchResults>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ExplorerActions.SET_ACTIVE_PAGE,
                        payload: response.data.page
                    },
                    {
                        type: ExplorerActions.SET_PAGINATION_DATA,
                        payload: response.data.pageData
                    },
                    {
                        type: ExplorerActions.SET_PAGINATION_LINKS,
                        payload: response.data.pageLinks
                    }
                ]
            } else {
                return [
                    {
                        type: ExplorerActions.SEARCH_FAILED
                    }
                ]
            }
        })
    );
}