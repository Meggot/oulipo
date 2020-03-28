import { Action } from '@ngrx/store';
import { PostboxModel } from '../../shared/models/postbox.model';

export const RETRIEVE_POSTBOX = "RETRIEVE_POSTBOX"
export const RETRIEVE_POSTBOX_FAILED = "RETRIEVE_POSTBOX_FAILED"
export const SET_POSTBOX = "SET_POSTBOX"
export const READ_POSTBOX = "READ_POSTBOX"
export const NOTIFICATION_LOGIN = "NOTIFICATION_LOGIN"
export const NOTIFICATION_LOGOUT = "NOTIFICATION_LOGOUT"
export const SUBSCRIBE_ON_ID_TYPES = "SUBSCRIBE_ON_ID_TYPES"

export class SubscribeOnIdTypes implements Action {
    readonly type = SUBSCRIBE_ON_ID_TYPES;

    constructor(public payload: {entityId: number, types: string[]}) {}
}

export class NotificationLogin implements Action {
    readonly type = NOTIFICATION_LOGIN;

    constructor() {}
}

export class NotificationLogout implements Action {
    readonly type = NOTIFICATION_LOGOUT;

    constructor() {}
}

export class RetrievePostbox implements Action {
    readonly type = RETRIEVE_POSTBOX;

    constructor() {}
}

export class RetrievePostboxFailed implements Action {
    readonly type = RETRIEVE_POSTBOX_FAILED;

    constructor(public payload: string) {}
}

export class SetPostbox implements Action {
    readonly type = SET_POSTBOX;

    constructor(public payload: PostboxModel) {}
}

export class ReadPostbox implements Action {
    readonly type = READ_POSTBOX;

    constructor() {}
}

export type NotificationActions =
RetrievePostbox |
RetrievePostboxFailed |
SetPostbox |
ReadPostbox |
NotificationLogin |
NotificationLogout |
SubscribeOnIdTypes;