import { MessageModel } from '../../shared/models/message.model';
import * as InboxActions from './inbox.actions';
export interface State {
    unreadMessages: MessageModel[],
    receivedMessages: MessageModel[],
    sentMessages: MessageModel[],
}

const initialState: State = {
    unreadMessages: [],
    receivedMessages: [],
    sentMessages: []
}

export function inboxReducer(state = initialState, action: InboxActions.InboxActions) {
    switch (action.type) {
        case InboxActions.ADD_UNREAD_MESSAGE:
            return {
                ...state,
                unreadMessages: [...state.unreadMessages, action.payload]
            }
        case InboxActions.ADD_UNREAD_MESSAGES:
            return {
                ...state,
                unreadMessages: [...state.unreadMessages, ...action.payload]
            }
        case InboxActions.ADD_SENT_MESSAGE:
            return {
                ...state,
                sentMessages: [...state.sentMessages, action.payload]
            }
        case InboxActions.ADD_SENT_MESSAGES:
            return {
                ...state,
                sentMessages: [...state.sentMessages, ...action.payload]
            }
        case InboxActions.ADD_RECEIVED_MESSAGE:
            return {
                ...state,
                receivedMessages: [...state.receivedMessages, action.payload]
            }
        case InboxActions.ADD_RECEIVED_MESSAGES:
            return {
                ...state,
                receivedMessages: [...state.receivedMessages, ...action.payload]
            }
        case InboxActions.INBOX_LOGOUT:
            return {
                ...state = initialState
            }
        case InboxActions.SET_THIS_INBOX:
            return {
                ...state,
                receivedMessages: action.payload.recievedMessages,
                sentMessages: action.payload.sentMessages
            }
        case InboxActions.READ_MESSAGE:
            return {
                ...state,
                receivedMessages: [
                    ...state.receivedMessages, state.unreadMessages[action.payload]
                ],
                unreadMessages: [
                    ...state.unreadMessages.slice(0, action.payload), ...state.unreadMessages.slice(action.payload + 1)
                ]
            }
        case InboxActions.SEND_MESSAGE:
            return {
                ...state,
                sentMessages: [...state.sentMessages, action.payload]
            }
        default:
            return state;
    }
}