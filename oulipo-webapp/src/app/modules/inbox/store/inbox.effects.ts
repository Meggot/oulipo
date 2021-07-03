import { Injectable } from "@angular/core";
import { Actions, Effect, ofType } from '@ngrx/effects';
import { InboxApi } from './inbox.api';

import * as AccountActions from '../../accounts/store/account.actions';
import { map, switchMap, mergeMap } from 'rxjs/operators';
import { AccountModel } from '../../shared/models/account.model';
import * as InboxActions from '../../inbox/store/inbox.actions';
import { MessageModel } from '../../shared/models/message.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import * as NotificationActions from '../../notification/store/notification.actions'

@Injectable()
export class InboxEffects {

    constructor(private actions$: Actions,
        private inboxApi: InboxApi) {
    }

    @Effect()
    thisAccountLoadedGetAllMessages = this.actions$.pipe(
        ofType(AccountActions.SET_THIS_ACCOUNT),
        map((action: AccountActions.SetThisAccount) => {
            return action.payload;
        }),
        mergeMap((account: AccountModel) => {
            return [
                {
                    type: InboxActions.SET_THIS_INBOX,
                    payload: {recievedMessages: account.receivedMessages,
                        sentMessages: account.sentMessages}
                },
                {
                    type: NotificationActions.SUBSCRIBE_ON_ID_TYPES,
                    payload: {
                        entityId: account.idField,
                        types: [
                            'INBOX_MESSAGE_RECEIVED'
                        ]
                    }
                }
            ]
        })
    )
    @Effect()
    refreshInbox = this.actions$.pipe(
        ofType(InboxActions.REFRESH_THIS_INBOX),
        switchMap((action: InboxActions.RefreshThisInbox) => {
            return this.inboxApi.refreshInbox();
        }),
        mergeMap((response: ApiResponse<AccountModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: InboxActions.SET_THIS_INBOX,
                        payload: {recievedMessages: response.data.receivedMessages,
                            sentMessages: response.data.sentMessages}
                    }
                ]
            }
        })
    )

    @Effect()
    sendMessage = this.actions$.pipe(
        ofType(InboxActions.TRY_SEND_MESSAGE),
        map((action: InboxActions.TrySendMessage) => {
            return action.payload;
        }),
        switchMap((payload: { to: string, content: string }) => {
            return this.inboxApi.sendMessage(payload.to, payload.content);
        }),
        mergeMap((response: ApiResponse<MessageModel>) => {
            if (response.statusCode === 200) {
                return [
                    {
                        type: InboxActions.SEND_MESSAGE,
                        payload: response.data
                    },
                ];
            } else {
                return [
                    {
                        type: InboxActions.SEND_MESSAGE_FAILURE,
                    }
                ];
            }
        })
    )
}