import { GroupModel } from '../../shared/models/group.model';
import * as GroupActions from './groups.actions';

export interface State {
    groups: GroupModel[],
    groupSearchResults: GroupModel[],
    viewingGroup: GroupModel,
    message: string;
}

const initialState: State = {
    groups: [],
    viewingGroup: new GroupModel('Loading', -1, 'Loading..', 'Loading', [], []),
    groupSearchResults: [],
    message: ''
}

export function groupReducer(state = initialState, action: GroupActions.GroupActions) {
    switch (action.type) {
        case GroupActions.ADD_GROUP:
            return {
                ...state,
                groups: [...state.groups, action.payload]
            }
        case GroupActions.ADD_GROUPS:
            return {
                ...state,
                groups: [...state.groups, ...action.payload]
            }
        case GroupActions.SET_VIEWING_GROUP:
            return {
                ...state,
                viewingGroup: action.payload
            }
        case GroupActions.GROUPS_LOGOUT:
            return{
                ...state = initialState
            }
        case GroupActions.SET_GROUP_SEARCH_RESULT:
        return {
            ...state,
            groupSearchResults: action.payload
        }
        case GroupActions.GROUP_LOAD_ERROR:
        return {
            ...state,
            message: action.payload
        }
        default:
            return state;
    }
}