import { Injectable } from "@angular/core";
import { SessionService } from 'src/app/services/session.service';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { AuditModel } from '../../shared/models/audit.model';
import { map, catchError } from 'rxjs/operators';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import { of } from 'rxjs';

@Injectable()
export class AdminApi {

    pageSize = '1';
    headers;

    constructor(private http: HttpClient,
        private sessionService: SessionService) {
        this.headers = new HttpHeaders({
            'Content-Type': 'application/json'
        })
    }

    public navigateAuditPageUrl(url: string) {
        return this.http.get(url, { headers: this.headers }).pipe(
            map((response: { _links: PaginationLinks, page: PaginationData, _embedded?: { content: AuditModel[] } }) => {
                if (response._embedded === undefined) {
                    response._embedded = { content: [] }
                }
                return new ApiResponse<{ pageLinks: PaginationLinks, pageData: PaginationData, page: AuditModel[]}>(
                    { page: response._embedded.content, pageLinks: response._links, pageData: response.page },
                    200,
                    'Successfuly retrieved all audits'
                )
            }),
            catchError((error: HttpErrorResponse) => {
                console.log(error);
                return of(new ApiResponse<any>(
                    null,
                    error.status,
                    'Failed to retrieve any audit'
                ));
            })
        )
    }

    public loadAudits() {
        return this.http.get(this.sessionService.hostname + "/audit", { headers: this.headers }).pipe(
            map((response: { _links: PaginationLinks, page: PaginationData, _embedded?: { auditDtoList: AuditModel[] } }) => {
                if (response._embedded === undefined) {
                    response._embedded = { auditDtoList: [] }
                }
                return new ApiResponse<{ pageLinks: PaginationLinks, pageData: PaginationData, page: AuditModel[]}>(
                    { page: response._embedded.auditDtoList, pageLinks: response._links, pageData: response.page },
                    200,
                    'Successfuly retrieved all audits'
                )
            }),
            catchError((error: HttpErrorResponse) => {
                console.log(error);
                return of(new ApiResponse<any>(
                    null,
                    error.status,
                    'Failed to retrieve any audit'
                ));
            })
        )
    }
}