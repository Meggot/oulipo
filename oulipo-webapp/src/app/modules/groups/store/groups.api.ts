import { Injectable } from '@angular/core';
import { SessionService } from 'src/app/services/session.service';
import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { GroupModel } from '../../shared/models/group.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import { of, Observable } from 'rxjs';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { AccountGroupMembership } from '../../shared/models/account-group-membership.model';

@Injectable()
export class GroupsApi {

    headers: HttpHeaders;

    constructor(private sessionService: SessionService,
        private httpClient: HttpClient) {
        this.headers = new HttpHeaders({
            'Content-Type': 'application/json'
        });
    }

    postGroupMembership(username: string, groupId: number, role: string) {
        let postMembershipForm = new FormData();
        postMembershipForm.append("role", role);
        return this.httpClient.post(this.sessionService.hostname + "/users/accounts/" + username + "/group/" + groupId,
            postMembershipForm).pipe(
                map((response: AccountGroupMembership) => {
                    return new ApiResponse<AccountGroupMembership>(
                        response,
                        200,
                        'Posted group membership successfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<AccountGroupMembership>(
                        null,
                        error.status,
                        error.error.message
                    ))
                })
            )
    }

    getGroups() {
        return this.httpClient.get(this.sessionService.hostname + "/users/groups", { headers: this.headers })
            .pipe(
                map((response: { _links: PaginationLinks, page: PaginationData, _embedded?: { content: GroupModel[] } }) => {
                    if (response._embedded === undefined) {
                        response._embedded = { content: [] };
                    }
                    return new ApiResponse<GroupModel[]>(
                        response._embedded.content,
                        200,
                        'Successfuly retrieved projects'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<any>(
                        null,
                        error.status,
                        'Failed to retrieve all groups.'
                    ))
                })
            )
    }

    getGroupByGroupId(groupId: number) {
        return this.httpClient.get(this.sessionService.hostname + "/users/groups/" + groupId, { headers: this.headers })
            .pipe(
                map((response: GroupModel) => {
                    return new ApiResponse(
                        response,
                        200,
                        'Retrieved Group Successfully'
                    )
                },
                    catchError((error: HttpErrorResponse) => {
                        return of(new ApiResponse<any>(
                            null,
                            error.status,
                            'Failed to retrieve group by id ' + groupId
                        ))
                    })
                ))
    }

    searchGroupsByName(groupName: string) {
        let params = new HttpParams();
        params = params.append('name', groupName);
        return this.httpClient.get(this.sessionService.hostname + '/users/groups/search', { headers: this.headers, params: params })
            .pipe(
                map((response: { _links: PaginationLinks, page: PaginationData, _embedded?: { content: GroupModel[] } }) => {
                    if (response._embedded === undefined) {
                        response._embedded = { content: [] }
                    }
                    return new ApiResponse<GroupModel[]>(
                        response._embedded.content,
                        200,
                        'Searched groups successfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<GroupModel[]>(
                        null,
                        error.status,
                        'Search failed for groups'
                    ))
                })
            )
    }
}