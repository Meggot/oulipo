import { Injectable } from "@angular/core";
import { SessionService } from 'src/app/services/session.service';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpResponse, HttpParams } from '@angular/common/http';
import { ApiResponse } from '../../shared/models/api-response.model';
import { AuthorModel } from '../../shared/models/author.model';
import { map, catchError } from 'rxjs/operators';
import { of, Observable } from 'rxjs';
import { ProjectModel } from '../../shared/models/project.model';
import { HateosResponseModel } from '../../shared/models/hateos-response.model';
import { PartModel } from '../../shared/models/part.model';
import { EditModel } from '../../shared/models/edit.model';
import { CreateProjectModel } from '../../shared/models/create-project.model';
import { ProjectTagModel } from '../../shared/models/project-tag-model.model';
import { ProjectRolesModel } from '../../shared/models/project-roles.model';
import { AuditModel } from '../../shared/models/audit.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { SubErroredResponse } from '../../shared/models/subErroredResponse';

@Injectable()
export class ProjectApi {

    headers: HttpHeaders;

    constructor(private sessionService: SessionService,
        private http: HttpClient) {
        this.headers = new HttpHeaders({
            "Content-Type": 'application/json'
        })
    }

    retrieveAuditForIdAndType(id: number, type: string) {
        let formQuery = new HttpParams()
        formQuery = formQuery.append("entityId", id.toString());
        formQuery = formQuery.append("entityType", type)
        return this.http.get(this.sessionService.hostname + "/audit", {headers: this.headers, params: formQuery})
        .pipe(
            map((response: {_links: PaginationLinks, data: PaginationData,  _embedded?: {content: AuditModel[]}}) => {
                if (response._embedded === undefined) {
                    response._embedded = {content: []};
                }
                return new ApiResponse<any>(
                    response,
                    200,
                    'retrieved entity hitsory'
                )
            }),
            catchError((error: HttpErrorResponse) => {
                return of (new ApiResponse<any>(
                    null,
                    error.status,
                    error.error.message
                ))
            })
        )
    }


    actionOnEdit(editId: number, action: string) {
        let formData = new FormData()
        formData.append('action', action);
        return this.http.patch(this.sessionService.hostname + "/projects/edits/" + editId + "/action", formData)
            .pipe(
                map((response: ApiResponse<EditModel>) => {
                    return new ApiResponse<EditModel>(
                        response.data,
                        200,
                        'Actioned edit successfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<EditModel>(
                        null,
                        error.status,
                        error.error.message
                    ))
                })
            )
    }

    deletePartById(partId: number) {
        return this.http.patch(this.sessionService.hostname + "/projects/parts/" + partId + "/delete", new FormData())
            .pipe(
                map((response: HttpResponse<null>) => {
                    return new ApiResponse<null>(
                        null,
                        200,
                        'Deleted part successfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<null>(
                        null,
                        error.status,
                        error.error.message
                    )
                    )
                })
            )
    }

    patchRoleRequestOnRoleId(roleId: number, newRole: string): Observable<ApiResponse<ProjectRolesModel>> {
        let patchRoleRequestForm = new FormData();
        patchRoleRequestForm.append("newRole", newRole);
        return this.http.patch(this.sessionService.hostname + "/projects/roles/" + roleId, patchRoleRequestForm)
            .pipe(
                map((response: ProjectRolesModel) => {
                    return new ApiResponse<ProjectRolesModel>(
                        response,
                        200,
                        'Patched role succesfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<ProjectRolesModel>(
                        null,
                        error.status,
                        error.error.message
                    ))
                })
            )
    }

    postRoleRequestOnProjectId(projectId: number, userId: number, role: string): Observable<ApiResponse<ProjectRolesModel>> {
        let postRoleRequestForm = new FormData();
        postRoleRequestForm.append("userId", userId.toString())
        postRoleRequestForm.append("authorProjectRoleType", role)
        return this.http.post(this.sessionService.hostname + "/projects/projects/" + projectId + "/roles", postRoleRequestForm)
            .pipe(
                map((response: ProjectRolesModel) => {
                    return new ApiResponse<ProjectRolesModel>(
                        response,
                        200,
                        'Posted role successfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    console.log(error)
                    return of(new ApiResponse<ProjectRolesModel>(
                        null,
                        error.status,
                        error.error.message
                    ))
                })
            )

    }

    postTagValueOnProjectId(projectId: number, tagValue: string) {
        let postValueForm = new FormData();
        postValueForm.append('value', tagValue)
        return this.http.post(this.sessionService.hostname + "/projects/projects/" + projectId + "/tags", postValueForm)
            .pipe(
                map((response: ProjectTagModel) => {
                    return new ApiResponse<ProjectTagModel>(
                        response,
                        200,
                        'Posted tag succesfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<ProjectTagModel>(
                        null,
                        error.status,
                        'Failed to post tag'
                    ))
                })
            )
    }

    postAnDeltaEditToCopy(copyId: number, delta: string) {
        let postDeltaForm = new FormData();
        postDeltaForm.append('delta', delta)
        return this.http.post(this.sessionService.hostname + "/projects/copy/" + copyId + "/edits", postDeltaForm)
            .pipe(
                map((response: EditModel) => {
                    return new ApiResponse<EditModel>(
                        response,
                        200,
                        'Edit has been posted, is in status: ' + response.status
                    )
                }),
                catchError((response: HttpErrorResponse) => {
                    return of(new ApiResponse<EditModel>(
                        null,
                        response.status,
                        response.error.message
                    ))
                })
            )
    }

    postAValueOntoPartId(id: number, value: string, reviewStatus: string): Observable<ApiResponse<PartModel>> {
        let postValueOnPartForm = new FormData();
        postValueOnPartForm.append('value', value);
        postValueOnPartForm.append('reviewStatus', reviewStatus);

        return this.http.patch(this.sessionService.hostname + "/projects/parts/" + id, postValueOnPartForm)
            .pipe(
                map((response: PartModel) => {
                    return new ApiResponse<PartModel>(
                        response,
                        200,
                        'Posted Value and Status onto part successfully'
                    )
                }),
                catchError((response: HttpErrorResponse) => {
                    return of(new ApiResponse<PartModel>(
                        null,
                        response.status,
                        'Failed to post a value and status of ' + reviewStatus + ' onto part id ' + id
                    ))
                })
            )
    }

    requestAPartOnProjectId(id: number): Observable<ApiResponse<PartModel>> {
        return this.http.post(this.sessionService.hostname + "/projects/parts/project/" + id + "/parts", { headers: this.headers })
            .pipe(
                map((response: PartModel) => {
                    return new ApiResponse<PartModel>(
                        response,
                        200,
                        'Requested a part successfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<PartModel>(
                        null,
                        error.status,
                        error.error.message
                    ))
                })
            )
    }

    loadProjectById(id: number): Observable<ApiResponse<ProjectModel>> {
        return this.http.get(this.sessionService.hostname + "/projects/projects/" + id, { headers: this.headers })
            .pipe(
                map((response: ProjectModel) => {
                    return new ApiResponse<ProjectModel>(
                        response,
                        200,
                        'Retrieved Project Successfully'
                    )
                }),
                catchError((response: HttpErrorResponse) => {
                    return of(new ApiResponse<ProjectModel>(
                        null,
                        response.status,
                        'Failed to retrieve project by the ID ' + id
                    )
                    )
                })
            )
    }

    loadAuthorById(id: number): Observable<ApiResponse<AuthorModel>> {
        return this.http.get(this.sessionService.hostname + "/projects/authors/" + id, { headers: this.headers })
            .pipe(
                map((response: AuthorModel) => {
                    return new ApiResponse<AuthorModel>(
                        response,
                        200,
                        'Retrieved author with ID ' + id + ' successfully'
                    )
                }),
                catchError((response: HttpErrorResponse) => {
                    return of(new ApiResponse<AuthorModel>(
                        null,
                        response.status,
                        'Failed to retrieve author with ID ' + id + 'successfully.'
                    ))
                })
            )
    }

    loadThisAuthor(): Observable<ApiResponse<AuthorModel>> {
        return this.http.get(this.sessionService.hostname + "/projects/authors/thisAuthor", { headers: this.headers })
            .pipe(
                map((response: AuthorModel) => {
                    return new ApiResponse<AuthorModel>(
                        response,
                        200,
                        'Retrieved author successfully'
                    )
                }),
                catchError((response: HttpErrorResponse) => {
                    return of(new ApiResponse<AuthorModel>(
                        null,
                        response.status,
                        'Failed to retrieve author.'
                    ))
                })
            )
    }

    createProject(createProject: CreateProjectModel): Observable<ApiResponse<any>> {
        let createProjectForm = new FormData();
        createProjectForm.append('title', createProject.title);
        createProjectForm.append('synopsis', createProject.synopsis);
        createProjectForm.append('visibilityType', createProject.visibilityType);
        createProjectForm.append('projectType', createProject.projectType);
        createProjectForm.append('sourcingType', createProject.sourcingType);

        return this.http.post(this.sessionService.hostname + "/projects/projects", createProjectForm)
            .pipe(
                map((response: ProjectModel) => {
                    return new ApiResponse<ProjectModel>(
                        response,
                        200,
                        'Created project successfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<SubErroredResponse>(
                        error.error,
                        error.status,
                        'Unable to create project.'
                    ))
                })
            )
    }

    getAllProjects(): Observable<ApiResponse<ProjectModel[]>> {
        return this.http.get(this.sessionService.hostname + "/projects/projects", { headers: this.headers })
            .pipe(
                map((response: HateosResponseModel<{ content: ProjectModel[] }>) => {
                    return new ApiResponse<ProjectModel[]>(
                        response._embedded.content,
                        200,
                        'Retrieved all projects successfully'
                    )
                }),
                catchError((response: HttpErrorResponse) => {
                    return of(new ApiResponse<ProjectModel[]>(
                        null,
                        response.status,
                        'Failed to retrieve all projects'
                    ))
                })
            )
    }
}