import { Action } from '@ngrx/store';
import { MessageModel } from '../../shared/models/message.model';

export const ADD_UNREAD_MESSAGE = "ADD_UNREAD_MESSAGE"
export const ADD_SENT_MESSAGE = "ADD_SENT_MESSAGE"
export const ADD_RECEIVED_MESSAGE = "ADD_RECEIVED_MESSAGE"
export const ADD_UNREAD_MESSAGES = "ADD_UNREAD_MESSAGES"
export const ADD_SENT_MESSAGES = "ADD_SENT_MESSAGES"
export const ADD_RECEIVED_MESSAGES = "ADD_RECEIVED_MESSAGES"
export const READ_MESSAGE = "READ_MESSAGE"
export const TRY_SEND_MESSAGE = "TRY_SEND_MESSAGE"
export const SEND_MESSAGE = "SEND_MESSAGE"
export const SEND_MESSAGE_FAILURE = "SEND_MESSAGE_FAILURE"
export const INBOX_LOGOUT = "INBOX_LOGOUT"
export const REFRESH_THIS_INBOX = "REFRESH_THIS_INBOX"
export const SET_THIS_INBOX = "SET_THIS_INBOX"

export class SetThisInbox implements Action {
    readonly type = SET_THIS_INBOX;

    constructor(public payload:{recievedMessages: MessageModel[],
    sentMessages: MessageModel[]}) {}
}

export class RefreshThisInbox implements Action {
    readonly type = REFRESH_THIS_INBOX;

    constructor() {}
}

export class AddUnreadMessage implements Action {
    readonly type = ADD_UNREAD_MESSAGE;

    constructor(public payload: MessageModel) {}
}

export class AddUnreadMessages implements Action {
    readonly type = ADD_UNREAD_MESSAGES;

    constructor(public payload: MessageModel[]) {}
}

export class AddSentMessage implements Action {
    readonly type = ADD_SENT_MESSAGE;

    constructor(public payload: MessageModel) {}
}

export class AddSentMessages implements Action {
    readonly type = ADD_SENT_MESSAGES;

    constructor(public payload: MessageModel[]) {}
}

export class AddReceivedMessage implements Action {
    readonly type = ADD_RECEIVED_MESSAGE;

    constructor(public payload: MessageModel) {}
}

export class AddReceivedMessages implements Action {
    readonly type =ADD_RECEIVED_MESSAGES;

    constructor(public payload: MessageModel[]) {}
}

export class ReadMessage implements Action {
    readonly type = READ_MESSAGE;
    //index of the message in unreadmessages that is read.
    constructor(public payload: number) {}
}

export class TrySendMessage implements Action {
    readonly type = TRY_SEND_MESSAGE;

    constructor(public payload: {to: string, content: string}) {}
}

export class SendMessage implements Action {
    readonly type = SEND_MESSAGE;

    constructor(public payload: MessageModel) {}
}

export class SendMessageFailure implements Action {
    readonly type = SEND_MESSAGE_FAILURE;

    constructor() {}
}

export class InboxLogout implements Action {
    readonly type = INBOX_LOGOUT;

    constructor() {}
}

export type InboxActions = 
AddUnreadMessage |
AddUnreadMessages |
AddSentMessage |
AddSentMessages |
AddReceivedMessage |
AddReceivedMessages |
ReadMessage |
TrySendMessage |
SendMessage |
InboxLogout |
RefreshThisInbox |
SetThisInbox |
SendMessageFailure;