import * as AuthActions from './auth.actions'

export interface State {
    message: string,
    token: string,
    authenticated: boolean,
    role: number
}

const initialState: State = {
    message: 'Initializing..',
    token: localStorage.getItem('token'),
    authenticated: Boolean(localStorage.getItem('authenticated')),
    role: +localStorage.getItem('role'),
};

export function authReducer(state = initialState, action: AuthActions.AuthActions) {
    console.log(action);
    switch (action.type) {
        case AuthActions.TRY_LOGIN:
            return {
                ...state,
                message: 'Attempting to login...'
            }
        case AuthActions.TRY_REGISTER:
            return {
                ...state,
                message: 'Attempting to register...'
            }
        case AuthActions.LOGIN_FAILED:
            return {
                ...state,
                authenticated: false,
                message: action.payload
            }
        case AuthActions.LOGIN:
            return {
                ...state,
                authenticated: true,
                message: 'Login Successful'
            }
        case AuthActions.LOGOUT:
            return {
                ...state,
                authenticated: false,
                message: 'Logged out',
                token: null
            }
        case AuthActions.SET_TOKEN:
            return {
                ...state,
                token: action.payload,
                role: 2
            }
        case AuthActions.REGISTER_FAILED:
            return {
                ...state,
                authenticated: false,
                message: action.payload
            }
        case AuthActions.REGISTER:
            return {
                ...state,
                authenticated: false,
                message: 'Successfully Registered!'
            }
        default:
            return state;
    }
}