import { Action } from '@ngrx/store'

export const TRY_REGISTER = "TRY_REGISTER"
export const TRY_LOGIN = "TRY_LOGIN"
export const LOGIN = "LOGIN";
export const LOGIN_FAILED = "LOGIN_FAILED"
export const LOGOUT = "AUTH-LOGOUT";
export const REGISTER = "REGISTER";
export const REGISTER_FAILED = "REGISTER_FAILED"
export const SET_TOKEN = "SET_TOKEN";


export class TryRegister implements Action {
    readonly type = TRY_REGISTER;
    
    constructor(public payload: {username: string, hashedPassword: string, email: string}) {}
}

export class TryLogin implements Action {
    readonly type = TRY_LOGIN;
    
    constructor(public payload: {username: string, hashedPassword: string}) {}
}

export class Login implements Action {
    readonly type = LOGIN;
    
    constructor() {}
}

export class LoginFailed implements Action {
    readonly type = LOGIN_FAILED;
    
    constructor(public payload: string) {}
}

export class Logout implements Action {
    readonly type = LOGOUT;

    constructor() {}
}

export class Register implements Action {
    readonly type = REGISTER;
}

export class RegisterFailed implements Action {
    readonly type = REGISTER_FAILED
    
    constructor(public payload: string) {}
}

export class SetToken implements Action {
    readonly type = SET_TOKEN;
    
    constructor(public payload: string) {}
}

export type AuthActions = 
Login | 
Logout | 
Register | 
SetToken | 
TryRegister | 
TryLogin | 
LoginFailed | 
RegisterFailed;