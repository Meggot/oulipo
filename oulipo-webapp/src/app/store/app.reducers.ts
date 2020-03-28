import * as fromAccounts from '../modules/accounts/store/account.reducers'
import * as fromAuth from '../modules/auth/store/auth.reducers'
import * as fromProjects from '../modules/projects/store/projects.reducers';
import * as fromExplorer from '../modules/explore/store/explorer.reducers';
import * as fromInbox from '../modules/inbox/store/inbox.reducers';
import * as fromGroups from '../modules/groups/store/groups.reducer';
import * as fromAdmin from '../modules/admin/store/admin.reducers'
import * as fromNotification from '../modules/notification/store/notification.reducers'
import { ActionReducerMap } from '@ngrx/store';

export interface AppState {
    accounts: fromAccounts.State;
    auth: fromAuth.State;
    projects: fromProjects.State;
    explorer: fromExplorer.State;
    inbox: fromInbox.State;
    groups: fromGroups.State;
    admin: fromAdmin.State;
    notifications: fromNotification.State;
}

export const reducers: ActionReducerMap<AppState> = {
    accounts: fromAccounts.accountReducer,
    auth: fromAuth.authReducer,
    projects: fromProjects.projectReducer,
    explorer: fromExplorer.explorerReducer,
    inbox: fromInbox.inboxReducer,
    groups: fromGroups.groupReducer,
    admin: fromAdmin.adminReducer,
    notifications: fromNotification.notificationReducer
}