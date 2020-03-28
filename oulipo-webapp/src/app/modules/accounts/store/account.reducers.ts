import * as AccountActions from './account.actions';
import { AccountModel } from '../../shared/models/account.model';

export interface State {
    accounts: AccountModel[];
    thisAccount: AccountModel;
    viewingAccount: AccountModel;
    searchResults: AccountModel[];
    message: string;
}

const initialState: State = {
    accounts: [],
    thisAccount: new AccountModel(-1, 'Loading', 'Loading@Loading', 'Loading',
        [], [], [], [], []),
    viewingAccount: new AccountModel(-1, 'Loading', 'Loading@Loading', 'Loading',
        [], [], [], [], []),
    searchResults: [],
    message: ''
}

export function accountReducer(state = initialState, action: AccountActions.AccountActions) {
    switch (action.type) {
        case AccountActions.ADD_ACCOUNT:
            return {
                ...state,
                accounts: [...state.accounts, action.payload]
            }
        case AccountActions.SET_THIS_ACCOUNT:
            return {
                ...state,
                accounts: [...state.accounts, action.payload],
                thisAccount: action.payload
            }
        case AccountActions.SET_VIEWING_ACCOUNT:
            return {
                ...state,
                viewingAccount: action.payload
            }
        case AccountActions.SET_THIS_ACCOUNT_AS_VIEWING:
            return {
                ...state,
                viewingAccount: state.thisAccount
            }
        case AccountActions.LOGOUT:
            return {
                ...state = initialState
            }
        case AccountActions.SET_SEARCH_RESULTS:
            return {
                ...state,
                searchResults: action.payload
            }
        case AccountActions.SET_ACCOUNTS_MESSAGE:
            return {
                ...state,
                message: action.payload
            }
        case AccountActions.CLEAR_ACCOUNTS_MESSAGE:
            return {
                ...state,
                message: ''
            }
        default:
            return {
                ...state
            }
    }
}