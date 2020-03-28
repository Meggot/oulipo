import { PostboxModel } from '../../shared/models/postbox.model';
import * as NotificationAction from '../store/notification.actions'

export interface State {
    postbox: PostboxModel;
    retrieveMessage: string;
}

const initialState: State = {
    postbox: new PostboxModel(-1, "", "", -1, "", [], [], false),
    retrieveMessage: ''
}
export function notificationReducer(state = initialState, action: NotificationAction.NotificationActions) {
    switch (action.type) {
        case NotificationAction.READ_POSTBOX:
            return {
                ...state,
                postbox: new PostboxModel(-1, "", "", -1, "", [], [], false)
            }
        case NotificationAction.RETRIEVE_POSTBOX_FAILED:
            return {
                ...state,
                retrieveMessage: action.payload
            }
        case NotificationAction.SET_POSTBOX:
            return {
                ...state,
                postbox: action.payload
            }
        default:
            return state;
    }
}
