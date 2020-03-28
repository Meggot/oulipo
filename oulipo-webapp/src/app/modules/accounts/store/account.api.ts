import { SessionService } from 'src/app/services/session.service';
import { Injectable } from '@angular/core';
import { ApiResponse } from '../../shared/models/api-response.model';
import { AccountModel } from '../../shared/models/account.model';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams, HttpResponse } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { AccountRelationshipModel } from '../../shared/models/account-relationship.model';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';

@Injectable()
export class AccountApi {

    headers: HttpHeaders;

    constructor(private sessionService: SessionService,
        private httpClient: HttpClient) {
        this.headers = new HttpHeaders({
            'Content-Type': 'application/json'
        });
    }

    search(username: string): Observable<ApiResponse<AccountModel[]>> {
        let params = new HttpParams();
        params = params.append("username", username);
        return this.httpClient.get(this.sessionService.hostname + "/users/accounts/search", {headers: this.headers, params: params})
        .pipe(
            map((response: {_links: PaginationLinks, page: PaginationData, _embedded?: {content: AccountModel[]}}) => {
                return new ApiResponse<AccountModel[]>(
                    response._embedded.content,
                    200,
                    'Searched account successfully'
                )
            }),
            catchError((error: HttpErrorResponse) => {
                return of(new ApiResponse<AccountModel[]>(
                    null,
                    error.status,
                    'Search failed unsuccessfully'
                ))
            })
        )
    }

    actionOnRelationship(id: number, action: string) {
        let requestAction = new FormData();
        requestAction.append('status', action)
        return this.httpClient.patch(this.sessionService.hostname + "/users/relationship/" + id, requestAction)
        .pipe(
            map((response: AccountRelationshipModel) => {
                return new ApiResponse<AccountRelationshipModel>(
                    response,
                    200,
                    'Posted action on a relationship successfully'
                )
            }),
            catchError((error: HttpErrorResponse) => {
                return of(new ApiResponse<AccountRelationshipModel>(
                    null,
                    error.status,
                    error.error.message
                ))
            })
        )
    }

    requestRelationship(type: string, username: string): Observable<ApiResponse<AccountRelationshipModel>> {
        let requestRelationship = new FormData();
        requestRelationship.append('type', type);
        return this.httpClient.post(this.sessionService.hostname + "/users/accounts/" + username + "/relationship", requestRelationship)
            .pipe(
                map((response: AccountRelationshipModel) => {
                    return new ApiResponse<AccountRelationshipModel>(
                        response,
                        200,
                        'Posted relationships request successfully.'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<AccountRelationshipModel>(
                        null,
                        error.status,
                        error.error.message
                    ))
                })
            )
    }

    loadThisAccount(): Observable<ApiResponse<AccountModel>> {
        return this.httpClient.get(this.sessionService.hostname + "/users/accounts/thisAccount", { headers: this.headers })
            .pipe(
                map((response: AccountModel) => {
                    return new ApiResponse<AccountModel>(
                        response,
                        200,
                        'This Account loaded successfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<AccountModel>(
                        null,
                        error.status,
                        'This Account failed to load'
                    ))
                }
                ))
    }

    loadAccountById(id: number): Observable<ApiResponse<AccountModel>> {
        return this.httpClient.get(this.sessionService.hostname + "/users/accounts/" + id, { headers: this.headers })
            .pipe(
                map((response: AccountModel) => {
                    return new ApiResponse<AccountModel>(
                        response,
                        200,
                        'Account by ID ' + id + ' loaded successfuly.'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<AccountModel>(
                        null,
                        error.status,
                        'Account by ID ' + id + ' failed to load'
                    ))
                })
            )
    }
}