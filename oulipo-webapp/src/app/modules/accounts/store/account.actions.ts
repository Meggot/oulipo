import { Action } from '@ngrx/store'
import { AccountModel } from '../../shared/models/account.model';

export const SEARCH_ACCOUNTS = "SEARCH_ACCOUNTS"
export const SET_SEARCH_RESULTS = "SET_SEARCH_RESULTS"
export const REQUEST_RELATIONSHIP = "REQUEST_RELATIONSHIP"
export const ACTION_ON_RELATIONSHIP = "ACTION_ON_RELATIONSHIP"
export const ERROR_ON_RELATIONSHIP = "ERROR_ON_RELATIONSHIP"
export const LOAD_AND_SET_VIEWING_ACCOUNT_BY_ID = "LOAD_AND_SET_VIEWING_ACCOUNT_BY_ID"
export const LOAD_ACCOUNT_BY_ID = "LOAD_ACCOUNT_BY_ID"
export const ADD_ACCOUNT = "ADD_ACCOUNT";
export const ADD_ACCOUNTS = "ADD_ACCOUNTS";
export const SET_THIS_ACCOUNT = "SET_THIS_ACCOUNT";
export const SET_VIEWING_ACCOUNT = "SET_VIEWING_ACCOUNT"
export const THIS_ACCOUNT_LOAD = "THIS_ACCOUNT_LOAD";
export const THIS_ACCOUNT_LOADED = "THIS_ACCOUNT_LOADED";
export const LOAD_FAILED = "LOAD_FAILED"
export const SET_THIS_ACCOUNT_AS_VIEWING = "SET_THIS_ACCOUNT_AS_VIEWING"
export const LOGOUT = "ACCOUNTS-LOGOUT"
export const SET_ACCOUNTS_MESSAGE = "SET_ACCOUNTS_MESSAGE"
export const CLEAR_ACCOUNTS_MESSAGE = "CLEAR_ACCOUNTS_MESSAGE"

export class ClearAccountsMessage implements Action {
    readonly type = CLEAR_ACCOUNTS_MESSAGE;

    constructor() {}
}

export class SetAccontsMessage implements Action {
    readonly type = SET_ACCOUNTS_MESSAGE;
    
    constructor(public payload: string) {}
}

export class ErrorOnRelationship implements Action {
    readonly type = ERROR_ON_RELATIONSHIP;

    constructor() {}
}

export class ActionOnRelationship implements Action {
    readonly type = ACTION_ON_RELATIONSHIP;

    constructor(public payload: {id: number, action: string}) {}
}

export class SearchAccounts implements Action {
    readonly type = SEARCH_ACCOUNTS;

    constructor(public payload: string) {}
}

export class SetSearchResults implements Action {
    readonly type = SET_SEARCH_RESULTS;

    constructor(public payload: AccountModel[]) {}
}

export class RequestRelationship implements Action {
    readonly type = REQUEST_RELATIONSHIP;

    constructor(public payload: {type: string, username: string}) {}
}

export class Logout implements Action {
    readonly type = LOGOUT;

    constructor() {}
}

export class LoadAccountById implements Action {
    readonly type = LOAD_ACCOUNT_BY_ID;

    constructor(public payload: number) {}
}

export class SetThisAccountAsViewing implements Action {
    readonly type = SET_THIS_ACCOUNT_AS_VIEWING;

    constructor() {}
}

export class ThisAccountLoaded implements Action {
    readonly type = THIS_ACCOUNT_LOADED;

    constructor() {}
}

export class LoadAndSetViewingAccountById implements Action {
    readonly type = LOAD_AND_SET_VIEWING_ACCOUNT_BY_ID;

    constructor(public payload: number) {}
}
export class SetViewingAccount implements Action {
    readonly type = SET_VIEWING_ACCOUNT;

    constructor(public payload: AccountModel) {}
}
export class AddAccount implements Action {
    readonly type = ADD_ACCOUNT;
    
    constructor(public payload: AccountModel) {}
}

export class AddAccounts implements Action {
    readonly type = ADD_ACCOUNTS;

    constructor(public payload: AccountModel[]) {}
}

export class ThisAccountLoad implements Action {
    readonly type = THIS_ACCOUNT_LOAD;
}

export class LoadFailed implements Action {
    readonly type = LOAD_FAILED;
}

export class SetThisAccount implements Action {
    readonly type = SET_THIS_ACCOUNT;
    
    constructor(public payload: AccountModel) {}
}

export type AccountActions = 
AddAccount | 
AddAccounts |
SetThisAccount | 
ThisAccountLoad | 
LoadFailed | 
SetViewingAccount | 
SetThisAccountAsViewing | 
ThisAccountLoaded |
Logout |
RequestRelationship |
ActionOnRelationship |
SearchAccounts |
SetSearchResults |
ErrorOnRelationship |
SetAccontsMessage |
ClearAccountsMessage;