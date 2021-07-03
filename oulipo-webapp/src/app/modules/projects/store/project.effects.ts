import { Injectable } from '@angular/core';
import { Actions, ofType, Effect } from '@ngrx/effects';
import * as ProjectActions from './project.actions';
import { switchMap, mergeMap, map, flatMap, merge } from 'rxjs/operators';

import { ApiResponse } from '../../shared/models/api-response.model';
import { ProjectApi } from './project.api';
import { AuthorModel } from '../../shared/models/author.model';
import { ProjectModel } from '../../shared/models/project.model';
import { PartModel } from '../../shared/models/part.model';
import { Router } from '@angular/router';
import { EditModel } from '../../shared/models/edit.model';
import { CreateProjectModel } from '../../shared/models/create-project.model';
import { ProjectTagModel } from '../../shared/models/project-tag-model.model';
import { ProjectRolesModel } from '../../shared/models/project-roles.model';
import { Action } from 'rxjs/internal/scheduler/Action';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { AuditModel } from '../../shared/models/audit.model';
import { timer } from 'rxjs';

@Injectable()
export class ProjectEffects {

    constructor(private actions$: Actions,
        private projectApi: ProjectApi,
        private router: Router,
        private store: Store<AppState>) {
    }


    @Effect()
    projectLoad = this.actions$.pipe(
        ofType(ProjectActions.LOAD),
        flatMap((action: ProjectActions.ProjectLoad) => [
            new ProjectActions.LoadThisAuthor(),
            new ProjectActions.ProjectLoad()
        ])
    );

    @Effect()
    retrieveProjectHistory = this.actions$.pipe(
        ofType(ProjectActions.RETRIEVE_PROJECT_HISTORY),
        map((action: ProjectActions.RetrieveProjectHistory) => {
            return action.payload;
        }),
        switchMap((payload: number) => {
            return this.projectApi.retrieveAuditForIdAndType(payload, 'PROJECT')
        }),
        mergeMap((response: ApiResponse<{ _links: PaginationLinks, data: PaginationData, _embedded?: { content: AuditModel[] } }>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.SET_PROJECT_HISTORY_MESSAGE,
                        payload: response.message
                    },
                    {
                        type: ProjectActions.SET_PROJECT_HISTORY_PAGE,
                        payload: response.data._embedded.content
                    },
                    {
                        type: ProjectActions.SET_PROJECT_HISTORY_PAGE_LINKS,
                        payload: response.data._links
                    },
                    {
                        type: ProjectActions.SET_PROJECT_HISTORY_PAGE_DATA,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.SET_PROJECT_HISTORY_MESSAGE,
                        payload: response.message
                    }
                ]
            }
        }
        ))

    @Effect()
    actionOnEdit = this.actions$.pipe(
        ofType(ProjectActions.ACTION_ON_EDIT),
        map((action: ProjectActions.ActionOnEdit) => {
            return action.payload;
        }),
        switchMap((payload: { editId: number, action: string }) => {
            return this.projectApi.actionOnEdit(payload.editId, payload.action);
        }),
        mergeMap((response: ApiResponse<EditModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.REFRESH_VIEWING_PROJECT
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.SET_EDIT_MESSAGE,
                        payload: response.message
                    }
                ]
            }
        })
    )

    @Effect()
    deletePartOnRoleId = this.actions$.pipe(
        ofType(ProjectActions.DELETE_PART),
        map((action: ProjectActions.DeletePart) => {
            return action.payload;
        }),
        switchMap((payload: number) => {
            return this.projectApi.deletePartById(payload);
        }),
        mergeMap((response: ApiResponse<any>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.SET_EDIT_MESSAGE,
                        payload: 'Deleting...'
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.SET_EDIT_MESSAGE,
                        payload: response.message
                    }
                ]
            }
        })
    )

    @Effect()
    handleClearMessageAfterTimeout = this.actions$.pipe(
        ofType(ProjectActions.SET_EDIT_MESSAGE),
        switchMap((action: ProjectActions.SetEditMessage) => {
            return timer(5000);
        }),
        mergeMap(() => {
            return [
                {
                    type: ProjectActions.SET_EDIT_MESSAGE,
                    payload: ''
                }
            ]
        })
    )

    @Effect()
    patchRoleOnRoleId = this.actions$.pipe(
        ofType(ProjectActions.PATCH_ROLE_ON_PROJECT),
        map((action: ProjectActions.PatchRoleOnProject) => {
            return action.payload;
        }),
        switchMap((payload: { roleId: number, newRole: string }) => {
            return this.projectApi.patchRoleRequestOnRoleId(payload.roleId, payload.newRole)
        }),
        //@ts-ignore 
        mergeMap((response: ApiResponse<ProjectRolesModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.ADD_ROLE_TO_VIEWING_PROJECT,
                        payload: response.data
                    },
                    {
                        type: ProjectActions.REFRESH_VIEWING_PROJECT
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.POST_ROLE_ON_PROJECT_FAIL,
                        payload: response.message
                    }
                ]
            }
        })
    )

    @Effect()
    postRoleReqOnProjectId = this.actions$.pipe(
        ofType(ProjectActions.POST_ROLE_ON_PROJECT),
        map((action: ProjectActions.PostRoleOnProject) => {
            return action.payload;
        }),
        switchMap((payload: { projectId: number, userId: number, role: string }) => {
            return this.projectApi.postRoleRequestOnProjectId(payload.projectId, payload.userId, payload.role).pipe(
                map((response: ApiResponse<ProjectRolesModel>) => {
                    return { payload: { projectId: payload.projectId, response: response } }
                })
            );
        }),
        //@ts-ignore
        mergeMap((response: { payload: { projectId: number, response: ApiResponse<ProjectRolesModel> } }) => {
            if (response.payload.response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.LOAD_AND_SET_VIEWING_PROJECT_BY_ID,
                        payload: response.payload.projectId
                    },
                    {
                        type: ProjectActions.ADD_ROLE_TO_VIEWING_PROJECT,
                        payload: response.payload.response.data
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.POST_ROLE_ON_PROJECT_FAIL,
                        payload: response.payload.response.message
                    }
                ]
            }
        })
    )

    @Effect({ dispatch: false })
    postTagValueOnProjectId = this.actions$.pipe(
        ofType(ProjectActions.POST_TAG_VALUE_ON_PROJECT),
        map((action: ProjectActions.PostTagValueOnProject) => {
            return action.payload;
        }),
        switchMap((payload: { projectId: number, value: string }) => {
            return this.projectApi.postTagValueOnProjectId(payload.projectId, payload.value)
        }),
        mergeMap((response: ApiResponse<ProjectTagModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.LOAD_AND_SET_VIEWING_PROJECT_BY_ID
                    }
                ]
            }
        })
    )

    @Effect()
    postEdit = this.actions$.pipe(
        ofType(ProjectActions.SUBMIT_EDIT),
        map((action: ProjectActions.SubmitEdit) => {
            return action.payload;
        }),
        switchMap((payload: { deltaString: string, copyId: number }) => {
            return this.projectApi.postAnDeltaEditToCopy(payload.copyId, payload.deltaString)
        }),
        mergeMap((response: ApiResponse<EditModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.LOAD_AND_SET_VIEWING_PROJECT_BY_ID,
                        payload: response.data.projectId
                    },
                    {

                        type: ProjectActions.SET_EDIT_MESSAGE,
                        payload: response.message

                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.SET_EDIT_MESSAGE,
                        payload: response.message
                    }
                ]
            }
        })
    )

    @Effect()
    createProject = this.actions$.pipe(
        ofType(ProjectActions.CREATE_PROJECT),
        map((action: ProjectActions.CreateProject) => {
            return action.payload;
        }),
        switchMap((payload: CreateProjectModel) => {
            return this.projectApi.createProject(payload);
        }),
        mergeMap((response: ApiResponse<any>) => {
            if (response.statusCode === 200) {
                this.router.navigate(['projects', response.data.projectId])
                return [
                    {
                        type: ProjectActions.ADD_AUTHORED_PROJECT,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.CREATE_PROJECT_FAIL,
                        payload: response.data
                    }
                ]
            }
        })

    )

    @Effect()
    requestPartOnProject = this.actions$.pipe(
        ofType(ProjectActions.REQUEST_PART_ON_PROJECT_ID),
        map((action: ProjectActions.RequestPartOnProjectId) => {
            return action.payload;
        }),
        switchMap((payload: number) => {
            return this.projectApi.requestAPartOnProjectId(payload);
        }),
        //@ts-ignore
        mergeMap((response: ApiResponse<PartModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.ADD_AUTHORED_PART,
                        payload: response.data
                    },
                    {
                        type: ProjectActions.LOAD_AND_SET_VIEWING_PROJECT_BY_ID,
                        payload: response.data.projectId
                    },
                    {
                        type: ProjectActions.SET_EDIT_MESSAGE,
                        payload: 'Part requested succesfully'
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.REQUEST_PART_FAILED,
                        payload: response.message
                    }
                ]
            }
        })
    )

    @Effect()
    postAValueAndReviewStatusOnPartId = this.actions$.pipe(
        ofType(ProjectActions.POST_PART_VALUE_AND_REVIEW_STATUS_ON_PART_ID),
        map((action: ProjectActions.PostPartValueAndReviewStatusOnPartId) => {
            return action.payload
        }),
        switchMap((payload: { partId: number, value: string, reviewStatus: string }) => {
            return this.projectApi.postAValueOntoPartId(payload.partId, payload.value, payload.reviewStatus);
        }),
        mergeMap((response: ApiResponse<PartModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.LOAD_AND_SET_VIEWING_PROJECT_BY_ID,
                        payload: response.data.projectId
                    },
                    {
                        type: ProjectActions.SET_EDIT_MESSAGE,
                        payload: 'Posted part!'
                    }
                ]
            }
        })
    )

    @Effect()
    thisAuthorLoad = this.actions$.pipe(
        ofType(ProjectActions.LOAD_THIS_AUTHOR),
        switchMap((action: ProjectActions.LoadThisAuthor) => {
            return this.projectApi.loadThisAuthor();
        }),
        mergeMap((response: ApiResponse<AuthorModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.ADD_AUTHORED_PROJECTS,
                        payload: response.data.createdProjects
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.LOAD_FAILED
                    }
                ]
            }
        })
    )

    @Effect()
    thisProjectsLoad = this.actions$.pipe(
        ofType(ProjectActions.LOAD_PROJECTS),
        switchMap((action: ProjectActions.ProjectLoad) => {
            return this.projectApi.getAllProjects();
        }),
        mergeMap((response: ApiResponse<ProjectModel[]>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.ADD_PROJECTS,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.LOAD_FAILED
                    }
                ]
            }
        })
    )

    @Effect()
    authorByIdLoad = this.actions$.pipe(
        ofType(ProjectActions.FIND_AUTHOR_BY_ID),
        map((action: ProjectActions.FinddAuthorById) => {
            return action.payload;
        }),
        switchMap((id: number) => {
            return this.projectApi.loadAuthorById(id);
        }),
        mergeMap((response: ApiResponse<AuthorModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.SET_VIEWING_AUTHOR,
                        payload: response.data
                    },
                    {
                        type: ProjectActions.ADD_AUTHOR,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.FIND_AUTHOR_FAILED
                    }
                ]
            }
        })
    );

    @Effect()
    projectLoadAndSet = this.actions$.pipe(
        ofType(ProjectActions.LOAD_AND_SET_VIEWING_PROJECT_BY_ID),
        map((action: ProjectActions.LoadandSetViewingProjectById) => {
            return action.payload;
        }),
        switchMap((id: number) => {
            return this.projectApi.loadProjectById(id);
        }),
        mergeMap((response: ApiResponse<ProjectModel>) => {
            if (response.statusCode === 200) {
                let nextToBePosted: PartModel = null;
                let parts = response.data.parts.filter(part => part.status === 'IN_PROGRESS');
                if (parts.length > 0) {
                    nextToBePosted = parts
                        .reduce((x, y) => {
                            return x.sequence < y.sequence ? x : y;
                        })
                }
                return [
                    {
                        type: ProjectActions.SET_VIEWING_PROJECT,
                        payload: response.data
                    },
                    {
                        type: ProjectActions.SET_VIEWING_COPY,
                        payload: response.data.copy
                    },
                    {
                        type: ProjectActions.SET_WRITING_PART,
                        payload: nextToBePosted
                    },
                    {
                        type: ProjectActions.RETRIEVE_PROJECT_HISTORY,
                        payload: response.data.projectId
                    }
                ]
            }
        })
    )

    @Effect()
    authorLoadAndSet = this.actions$.pipe(
        ofType(ProjectActions.LOAD_AND_SET_VIEWING_AUTHOR_BY_ID),
        map((action: ProjectActions.LoadAndSetViewingAuthorById) => {
            return action.payload;
        }),
        switchMap((id: number) => {
            return this.projectApi.loadAuthorById(id);
        }),
        mergeMap((response: ApiResponse<AuthorModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: ProjectActions.SET_VIEWING_AUTHOR,
                        payload: response.data
                    },
                    {
                        type: ProjectActions.ADD_AUTHOR,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: ProjectActions.LOAD_FAILED
                    }
                ]
            }
        }
        ));
}
