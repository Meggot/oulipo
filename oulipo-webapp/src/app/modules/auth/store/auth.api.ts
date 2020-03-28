import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { SessionService } from 'src/app/services/session.service';
import { catchError, map } from 'rxjs/operators';
import * as AuthActions from '../../auth/store/auth.actions'
import { of, Observable } from 'rxjs';
import { ApiResponse } from '../../shared/models/api-response.model';
import { AccountModel } from '../../shared/models/account.model';
import { SubErroredResponse } from '../../shared/models/subErroredResponse';


@Injectable()
export class AuthApi {

    headers: HttpHeaders;

    constructor(private httpClient: HttpClient,
        private sessionService: SessionService) {
        this.headers = new HttpHeaders({
            'Content-Type': 'application/json'
        });
    }

    postLogin(username: string, hashedPassword: string): Observable<ApiResponse<{ idToken: string, message: string }>> {
        let loginForm = new FormData();
        loginForm.append("username", username);
        loginForm.append("password", hashedPassword);
        return this.httpClient.post(this.sessionService.hostname + '/authenticate', loginForm).pipe(
            map((response: { idToken: string, message: string }) => {
                return new ApiResponse<any>(
                    response,
                    200,
                    'Login Successful');
            }),
            catchError((error: HttpErrorResponse) => {
                return of( new ApiResponse<any>(
                    null,
                    error.status,
                    'Invalid Username/Password'));
            })
        );
    }

    postRegister(authData: { username: string, hashedPassword: string, email: string }): Observable<ApiResponse<AccountModel>> {
        let registerForm = new FormData;
        registerForm.append("username", authData.username);
        registerForm.append("hashedPassword", authData.hashedPassword);
        registerForm.append("email", authData.email);
        return this.httpClient.post(this.sessionService.hostname + '/register', registerForm).pipe(
                map((response: AccountModel) => {
                    return new ApiResponse<any>(
                        response,
                        200,
                        'Account Created Successfully.');
                }),
                catchError((error: any) => {
                    console.log(error)
                    let subErrored = new SubErroredResponse(error.error.message, error.error.subErrors);
                    return of( new ApiResponse<any>(
                        null,
                        500,
                        subErrored.flattenSubErrors()));
                })
            );
    }
}