import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { AuthModule } from './modules/auth/auth.module';
import { SharedModule } from './modules/shared/shared.module';
import { ProjectsModule } from './modules/projects/projects.module';
import { AppRoutingModule } from './app-routing.module';
import { InboxModule } from './modules/inbox/inbox.module';
import { CoreModule } from './modules/core/core.module';
import { ExploreModule } from './modules/explore/explore.module';
import { AccountModule } from './modules/accounts/account.module';
import { StoreModule, Store } from '@ngrx/store';
import { reducers } from './store/app.reducers';
import { EffectsModule } from '@ngrx/effects';
import { AuthEffects } from './modules/auth/store/auth.effects';
import { AccountEffects } from './modules/accounts/store/account.effects';
import { ProjectEffects } from './modules/projects/store/project.effects';
import { ExplorerEffects } from './modules/explore/store/explorer.effects';
import { AppEffects } from './modules/core/components/app.effects';
import { InboxEffects } from './modules/inbox/store/inbox.effects';
import { GroupsModule } from './modules/groups/groups.module';
import { GroupsEffects } from './modules/groups/store/groups.effects';
import { AdminModule } from './modules/admin/admin.module';
import { AdminEffects } from './modules/admin/store/admin.effects';
import { NotificationModule } from './modules/notification/notification.module'
import { NotificationEffects } from './modules/notification/store/notification.effects';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    StoreModule.forRoot(reducers),
    EffectsModule.forRoot(
      [
        AuthEffects,
        AccountEffects,
        ProjectEffects,
        ExplorerEffects,
        InboxEffects,
        AppEffects,
        GroupsEffects,
        AdminEffects,
        NotificationEffects
      ]),
    // --- Project-Modules ---
    CoreModule,
    AuthModule,
    ProjectsModule,
    AccountModule,
    ExploreModule,
    InboxModule,
    AdminModule,
    GroupsModule,
    SharedModule,
    NotificationModule,
    AppRoutingModule
    ],
  bootstrap: [AppComponent]
})
export class AppModule {
 constructor(store: Store<any>) {
  store.subscribe( state => console.log('Initial App State: ', state));
 }
}
