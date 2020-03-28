import { Injectable } from "@angular/core";
import { Actions, Effect, ofType } from '@ngRx/effects';
import { NotificationApi } from './notification.api';
import * as NotificationActions from '../store/notification.actions'
import * as InboxActions from '../../inbox/store/inbox.actions'
import { map, mergeMap, switchMap, tap, flatMap } from 'rxjs/operators';
import { ApiResponse } from '../../shared/models/api-response.model';
import { PostboxModel } from '../../shared/models/postbox.model';
import { timer, of } from 'rxjs';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import { SubscriptionModel } from '../../shared/models/subscription.model';
import { AccountModel } from '../../shared/models/account.model';
import { NotificationMailModel } from '../../shared/models/notification.model';

@Injectable()
export class NotificationEffects {

    constructor(private actions$: Actions,
        private notificationApi: NotificationApi,
        private store: Store<AppState>) { }

    isPolling = false;
    hasCreated = false;

    // @Effect({ dispatch: false })
    // notificationLogin = this.actions$.pipe(
    //     ofType(NotificationActions.NOTIFICATION_LOGIN),
    //     map(() => {
    //         this.isPolling = true;
    //     })

    // switchMap((authData: { username: string, hashedPassword: string, email: string }) => {
    //     return this.authApi.postRegister(authData).pipe(
    //         map((response: ApiResponse<AccountModel>)=> {
    //             return {payload: {username: authData.username, hashedPassword: authData.hashedPassword}, response: response};
    //         })
    //     )
    // }),
    // )

    @Effect({ dispatch: false })
    subscribe = this.actions$.pipe(
        ofType(NotificationActions.SUBSCRIBE_ON_ID_TYPES),
        map((action: NotificationActions.SubscribeOnIdTypes) => {
            return action.payload;
        }),
        switchMap((payload: { entityId: number, types: string[] }) => {
            return this.notificationApi.subscribe(payload.entityId, payload.types);
        })
    )


    @Effect({ dispatch: false })
    dispatch = this.actions$.pipe(
        ofType(NotificationActions.NOTIFICATION_LOGIN),
        map(() => {
            this.isPolling = true;
        }),
        mergeMap(() => {
            return timer(0, 6000).pipe(
                map(() => {
                    if (this.isPolling) {
                        this.store.dispatch(new NotificationActions.RetrievePostbox())
                    }

                })
            )
        })
    )


    @Effect({ dispatch: false })
    notificationLogout = this.actions$.pipe(
        ofType(NotificationActions.NOTIFICATION_LOGOUT),
        map(() => {
            this.isPolling = false;
        })
    )




    @Effect()
    postboxRetrieve = this.actions$.pipe(
        ofType(NotificationActions.RETRIEVE_POSTBOX),
        switchMap(() => {
            return this.notificationApi.getPostBox();
        }),
        //@ts-ignore 
        mergeMap((response: ApiResponse<PostboxModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: NotificationActions.SET_POSTBOX,
                        payload: response.data
                    }
                ]
            } else {
                return [
                    {
                        type: NotificationActions.RETRIEVE_POSTBOX_FAILED,
                        payload: response.message
                    }
                ]
            }
        })
    )
}

export const INBOX_MESSAGE_RECEIVED = "INBOX_MESSAGE_RECEIVED"
export const PROJECT_TAG_CREATED = "PROJECT_TAG_CREATED"
export const PROJECT_TAG_REMOVED = "PROJECT_TAG_REMOVED"
export const PROJECT_PART_POSTED = "PROJECT_PART_POSTED"
export const PROJECT_PART_UPDATED = "PROJECT_PART_UPDATED"
export const PROJECT_EDIT_POSTED = "PROJECT_EDIT_POSTED"
export const PROJECT_EDIT_UPDATED = "PROJECT_EDIT_UPDATED"
export const AUTHOR_PART_UPDATED = "AUTHOR_PART_UPDATED"
export const AUTHOR_ROLE_UPDATED = "AUTHOR_ROLE_UPDATED"