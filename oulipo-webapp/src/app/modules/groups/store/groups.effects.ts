import { Injectable } from '@angular/core';
import { Actions, Effect, ofType } from '@ngRx/effects';
import { GroupsApi } from './groups.api';
import * as AccountActions from '../../accounts/store/account.actions';
import * as GroupActions from './groups.actions';
import { AccountModel } from '../../shared/models/account.model';
import { map, switchMap, mergeMap, flatMap } from 'rxjs/operators';
import { GroupModel } from '../../shared/models/group.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import * as AppActions from '../../core/components/app.actions'
import { Action } from 'rxjs/internal/scheduler/Action';
import { AccountGroupMembership } from '../../shared/models/account-group-membership.model';
@Injectable()
export class GroupsEffects {
    constructor(private actions$: Actions,
        private groupApi: GroupsApi) { }


    @Effect()
    logoutGroups = this.actions$.pipe(
        ofType(AppActions.APP_LOGOUT),
        flatMap((action: AppActions.Logout) => [
            new GroupActions.GroupLogout()
        ])
    );

    @Effect()
    postGroupMembership = this.actions$.pipe(
        ofType(GroupActions.POST_GROUP_MEMBERSHIP),
        map((action: GroupActions.PostGroupMembership) =>{
            return action.payload;
        }),
        switchMap((request: {groupId: number, username: string, role: string})=> {
            return this.groupApi.postGroupMembership(request.username, request.groupId, request.role)
        }),
        mergeMap((response: ApiResponse<AccountGroupMembership>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: GroupActions.GROUPS_LOAD
                    }
                ]
            } else {
                return [
                    {
                        type: GroupActions.GROUP_LOAD_ERROR,
                        payload: response.message
                    }
                ]
            }
        })
    )

    @Effect()
    searchGroups = this.actions$.pipe(
        ofType(GroupActions.SEARCH_GROUPS),
        map((action: GroupActions.SearchGroups) => {
            return action.payload;
        }),
        switchMap((groupName: string) => {
            return this.groupApi.searchGroupsByName(groupName);
        }),
        mergeMap((response: ApiResponse<GroupModel[]>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: GroupActions.SET_GROUP_SEARCH_RESULT,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: GroupActions.GROUP_LOAD_ERROR
                    }
                ]
            }
        })
    )
    
    @Effect()
    loadAllGroups = this.actions$.pipe(
        ofType(GroupActions.GROUPS_LOAD),
        switchMap(() => {
            return this.groupApi.getGroups();
        }),
        mergeMap((response: ApiResponse<GroupModel[]>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: GroupActions.ADD_GROUPS,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: GroupActions.GROUP_LOAD_ERROR
                    }
                ]
            }
        })
    )

    @Effect()
    loadAndSetViewingGroupById = this.actions$.pipe(
        ofType(GroupActions.LOAD_AND_SET_VIEWING_GROUP),
        map((action: GroupActions.LoadAndSetViewingGroup) => {
            return action.payload;
        }),
        switchMap((payload: number) => {
            return this.groupApi.getGroupByGroupId(payload);
        }),
        mergeMap((response: ApiResponse<GroupModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: GroupActions.SET_VIEWING_GROUP,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: GroupActions.GROUP_LOAD_ERROR
                    }
                ]
            }
        })
    )
}