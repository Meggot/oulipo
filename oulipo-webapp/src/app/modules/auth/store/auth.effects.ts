import { Effect, Actions, ofType } from '@ngrx/effects'
import { Injectable } from '@angular/core';

import * as AuthActions from '../../auth/store/auth.actions'
import * as AccountActions from '../../accounts/store/account.actions'
import { map, switchMap, mergeMap, tap } from 'rxjs/operators';
import { AccountModel } from '../../shared/models/account.model';
import { Router } from '@angular/router';
import { AuthApi } from './auth.api';
import { ApiResponse } from '../../shared/models/api-response.model';
import * as AppActions from '../../core/components/app.actions';

@Injectable()
export class AuthEffects {

    constructor(private actions$: Actions,
        private router: Router,
        private authApi: AuthApi) {
    }

    @Effect({ dispatch: false })
    logoutRouter = this.actions$.pipe(
        ofType(AuthActions.LOGOUT),
        tap((action: AuthActions.Logout) => {
            localStorage.removeItem("token")
            localStorage.removeItem("authenticated")
            localStorage.removeItem("role")        
            this.router.navigate(['auth', 'login'])
        })
    )

    @Effect()
    authRegister = this.actions$.pipe(
        ofType(AuthActions.TRY_REGISTER),
        map((action: AuthActions.TryRegister) => {
            return action.payload;
        }),
        switchMap((authData: { username: string, hashedPassword: string, email: string }) => {
            return this.authApi.postRegister(authData).pipe(
                map((response: ApiResponse<AccountModel>)=> {
                    return {payload: {username: authData.username, hashedPassword: authData.hashedPassword}, response: response};
                })
            )
        }),
        //@ts-ignore
        mergeMap((response: {payload: { username: string, hashedPassword: string}, response: ApiResponse<AccountModel>}) => {
            if (response.response.statusCode === 200) {
                return [
                    {
                        type: AuthActions.REGISTER
                    },
                    {
                        type: AuthActions.TRY_LOGIN,
                        payload: response.payload
                    },
                ]
            } else {
                return [
                    {
                        type: AuthActions.REGISTER_FAILED,
                        payload: response.response.message
                    },

                ]
            }
        })
    );

    @Effect()
    authLogin = this.actions$.pipe(
        ofType(AuthActions.TRY_LOGIN),
        map((action: AuthActions.TryLogin) => {
            return action.payload;
        }),
        switchMap((authData: { username: string, hashedPassword: string }) => {
            return this.authApi.postLogin(authData.username, authData.hashedPassword);
        }),
        mergeMap((response: ApiResponse<{ idToken: string, message: string }>) => {
            if (response.statusCode === 200) {
                localStorage.setItem('token', 'Bearer ' + response.data.idToken);
                localStorage.setItem('authenticated', 'true');
                localStorage.setItem('role', '2');
                this.router.navigate(['/'])
                return [
                    {
                        type: AuthActions.LOGIN
                    },
                    {
                        type: AuthActions.SET_TOKEN,
                        payload: 'Bearer ' + response.data.idToken
                    },
                    {
                        type: AccountActions.THIS_ACCOUNT_LOAD
                    }
                ]
            } else {
                return [{
                    type: AuthActions.LOGIN_FAILED,
                    payload: response.message
                }]
            }
        })
    )

}