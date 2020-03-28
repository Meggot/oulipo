import { Injectable } from '@angular/core';
import { ExplorerSearchQuery } from '../../shared/models/explorer-search-query.model';
import { HttpParams, HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { SessionService } from 'src/app/services/session.service';
import { HateosResponseModel } from '../../shared/models/hateos-response.model';
import { switchMap, map, catchError } from 'rxjs/operators';
import { ProjectModel } from '../../shared/models/project.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { of } from 'rxjs/internal/observable/of';

@Injectable()
export class ExplorerApi {

    pageSize = '20';
    headers;
    constructor(private http: HttpClient,
        private sessionService: SessionService) { 
            this.headers = new HttpHeaders({
                'Content-Type': 'application/json',
            });
        }


    public navigatePageUrl(url: string) {
        return this.http.get(url, {headers: this.headers}).pipe(
            map((response: {_links: PaginationLinks, page: PaginationData, _embedded?: {content: ProjectModel[]}}) => {
                if (response._embedded === undefined) {
                    response._embedded = {content: []};
                }
                return new ApiResponse<{pageLinks: PaginationLinks, pageData: PaginationData, page: ProjectModel[],  }>(
                    { page: response._embedded.content, pageLinks: response._links, pageData: response.page },
                    200,
                    'Successfuly retrieved projects'
                )
            }),
            catchError((error: HttpErrorResponse) => {
                console.log(error);
                return of(new ApiResponse<any>(
                    null,
                    error.status,
                    'Failed to retrieve any projects'
                ));
            })
        )
    }

    public search(exploreSearchForm: ExplorerSearchQuery) {
        let params = new HttpParams();
        if (exploreSearchForm.title != null) {
            params = params.set('title', exploreSearchForm.title)
        }
        if (exploreSearchForm.tags != null) {
            params = params.set('tags', exploreSearchForm.tags);
        }
        if (exploreSearchForm.type != null) {
            params = params.set('type', exploreSearchForm.type);
        }
        if (exploreSearchForm.author != null) {
            params = params.set('author', exploreSearchForm.author);
        }
        if (exploreSearchForm.sort != null) {
            params = params.set('sort', exploreSearchForm.sort);
        }
        params = params.set('size', this.pageSize);
        return this.http.get(this.sessionService.hostname + '/projects/explore', {headers: this.headers, params: params}).pipe(
            map((response: {_links: PaginationLinks, page: PaginationData, _embedded?: {content: ProjectModel[]}}) => {
                if (response._embedded === undefined) {
                    response._embedded = {content: []};
                }
                return new ApiResponse<{pageLinks: PaginationLinks, pageData: PaginationData, page: ProjectModel[],  }>(
                    { page: response._embedded.content, pageLinks: response._links, pageData: response.page },
                    200,
                    'Successfuly retrieved projects'
                )
            }),
            catchError((error: HttpErrorResponse) => {
                console.log(error);
                return of(new ApiResponse<any>(
                    null,
                    error.status,
                    'Failed to retrieve any projects'
                ));
            })
        );
    }
}