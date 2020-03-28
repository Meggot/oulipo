import { Injectable } from '@angular/core';
import { Actions, ofType, Effect } from '@ngRx/effects';
import { Router } from '@angular/router';
import { AccountApi } from './account.api';

import * as AccountActions from './account.actions';
import { switchMap, mergeMap, map, flatMap } from 'rxjs/operators';

import { AccountModel } from '../../shared/models/account.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import { SessionService } from 'src/app/services/session.service';
import { AccountRelationshipModel } from '../../shared/models/account-relationship.model';
import { timer, Observable } from 'rxjs';

@Injectable()
export class AccountEffects {

    constructor(private actions$: Actions,
        private router: Router,
        private accountApi: AccountApi,
        private sessionService: SessionService) {
    }

    @Effect()
    handleClearMessageAfterTimeout = this.actions$.pipe(
        ofType(AccountActions.SET_ACCOUNTS_MESSAGE),
        switchMap((action: AccountActions.SetAccontsMessage) => {
            return timer(5000);
        }),
        mergeMap(() => {
            return [
                {
                    type: AccountActions.CLEAR_ACCOUNTS_MESSAGE
                }
            ]
        })
    )

    @Effect()
    handleActionOnRequest = this.actions$.pipe(
        ofType(AccountActions.ACTION_ON_RELATIONSHIP),
        map((action: AccountActions.ActionOnRelationship) => {
            return action.payload;
        }),
        switchMap((payload: { id: number, action: string }) => {
            return this.accountApi.actionOnRelationship(payload.id, payload.action);
        }),
        mergeMap((response: ApiResponse<AccountRelationshipModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: AccountActions.THIS_ACCOUNT_LOAD
                    }
                ]
            } else {
                return [
                    {
                        type: AccountActions.ERROR_ON_RELATIONSHIP
                    },
                    {
                        type: AccountActions.SET_ACCOUNTS_MESSAGE,
                        payload: response.message
                    }
                ]
            }
        })
    )

    @Effect()
    searchAccounts = this.actions$.pipe(
        ofType(AccountActions.SEARCH_ACCOUNTS),
        map((action: AccountActions.SearchAccounts) => {
            return action.payload;
        }),
        switchMap((payload: string) => {
            return this.accountApi.search(payload);
        }),
        mergeMap((response: ApiResponse<AccountModel[]>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: AccountActions.SET_SEARCH_RESULTS,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: AccountActions.ERROR_ON_RELATIONSHIP
                    }
                ]
            }
        })
    )

    @Effect()
    requestRelationship = this.actions$.pipe(
        ofType(AccountActions.REQUEST_RELATIONSHIP),
        map((action: AccountActions.RequestRelationship) => {
            return action.payload;
        }),
        switchMap((payload: { type: string, username: string }) => {
            return this.accountApi.requestRelationship(payload.type, payload.username);
        }),
        mergeMap((response: ApiResponse<AccountRelationshipModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: AccountActions.THIS_ACCOUNT_LOAD
                    }
                ]
            } else {
                return [
                    {
                        type: AccountActions.ERROR_ON_RELATIONSHIP
                    },
                    {
                        type: AccountActions.SET_ACCOUNTS_MESSAGE,
                        payload: response.message
                    }
                ]
            }
        })
    )

    @Effect()
    authRegister = this.actions$.pipe(
        ofType(AccountActions.THIS_ACCOUNT_LOAD),
        switchMap(() => {
            return this.accountApi.loadThisAccount();
        }),
        mergeMap((response: ApiResponse<AccountModel>) => {
            if (response.statusCode === 200) {
                this.sessionService.username = response.data.username;
                return [
                    {
                        type: AccountActions.SET_THIS_ACCOUNT,
                        payload: response.data
                    },
                    {
                        type: AccountActions.THIS_ACCOUNT_LOADED
                    }
                ]
            } else {
                return [
                    {
                        type: AccountActions.LOAD_FAILED
                    }
                ]
            }
        })
    )

    @Effect()
    accountByIdLoad = this.actions$.pipe(
        ofType(AccountActions.LOAD_ACCOUNT_BY_ID),
        map((action: AccountActions.LoadAccountById) => {
            return action.payload;
        }),
        switchMap((id: number) => {
            return this.accountApi.loadAccountById(id);
        }),
        mergeMap((response: ApiResponse<AccountModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: AccountActions.ADD_ACCOUNT,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: AccountActions.LOAD_FAILED
                    }
                ]
            }
        })
    )

    @Effect()
    loadAccountAndSetViewing = this.actions$.pipe(
        ofType(AccountActions.LOAD_AND_SET_VIEWING_ACCOUNT_BY_ID),
        map((action: AccountActions.LoadAccountById) => {
            return action.payload;
        }),
        switchMap((id: number) => {
            return this.accountApi.loadAccountById(id);
        }),
        mergeMap((response: ApiResponse<AccountModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: AccountActions.SET_VIEWING_ACCOUNT,
                        payload: response.data
                    },
                    {
                        type: AccountActions.ADD_ACCOUNT,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: AccountActions.LOAD_FAILED
                    }
                ]
            }
        })
    )

}
