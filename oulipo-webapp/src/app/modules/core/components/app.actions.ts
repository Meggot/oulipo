import { Action } from '@ngrx/store';

export const LOAD_APP = "LOAD_APP";
export const SELECT_USER = "SELECT_USER";
export const APP_LOGOUT = "APP_LOGOUT";

export class LoadApp implements Action {
    readonly type = LOAD_APP;

    constructor() {}
}

export class SelectUser implements Action {
    readonly type = SELECT_USER;

    constructor(public payload: number) {}
}

export class Logout implements Action {
    readonly type = APP_LOGOUT;

    constructor() {}
}


export type AppActions =  LoadApp | SelectUser | Logout;