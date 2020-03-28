import { Injectable } from '@angular/core';
import { Actions, ofType, Effect } from '@ngRx/effects';
import * as AppActions from './app.actions'
import { flatMap, map } from 'rxjs/operators';
import * as GroupActions from '../../groups/store/groups.actions'
import * as ProjectActions from '../../projects/store/project.actions'
import * as AccountActions from '../../accounts/store/account.actions'
import * as AuthActions from '../../auth/store/auth.actions'
import * as ExploreActions from '../../explore/store/explorer.actions'
import * as InboxActions from '../../inbox/store/inbox.actions'
import * as NotificationActions from '../../notification/store/notification.actions'
@Injectable()
export class AppEffects {

    constructor(private actions$: Actions) {
    }

    @Effect()
    logout = this.actions$.pipe(
        ofType(AppActions.APP_LOGOUT),
        flatMap((action: AppActions.Logout) => [
            new AccountActions.Logout(),
            new ProjectActions.Logout(),
            new AuthActions.Logout(),
            new InboxActions.InboxLogout(),
            new NotificationActions.NotificationLogout()
        ])
    )

    @Effect()
    loadEffect = this.actions$.pipe(
        ofType(AppActions.LOAD_APP),
        flatMap((action: AppActions.LoadApp) => [
            new AccountActions.ThisAccountLoad(),
        ])
    );

    @Effect()
    loadAfterAccounts = this.actions$.pipe(
        ofType(AccountActions.THIS_ACCOUNT_LOADED),
        flatMap((action: AccountActions.ThisAccountLoaded) => [
            new ProjectActions.Load(),
            new GroupActions.GroupsLoad(),
            new NotificationActions.NotificationLogin(),
        ])
    )

    @Effect()
    newViewingUser = this.actions$.pipe(
        ofType(AppActions.SELECT_USER),
        map((action: AppActions.SelectUser) => {
            return action.payload
        }),
        flatMap((payload: number) => [
            new AccountActions.LoadAndSetViewingAccountById(payload),
            new ProjectActions.LoadAndSetViewingAuthorById(payload)
        ])
    )
}
