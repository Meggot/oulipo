import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

import { AccountContainerComponent } from 'src/app/modules/accounts/account-container/account-container.component';
import { AccountComponentComponent } from 'src/app/modules/accounts/components/account-component/account-component.component';
import { AccountInformationComponent } from './components/account-selected-components/account-information/account-information.component';
import { AccountProjectsComponent } from './components/account-selected-components/account-projects/account-projects.component';
import { AccountPartsComponent } from './components/account-selected-components/account-parts/account-parts.component';
import { AccountCommentsComponent } from './components/account-selected-components/account-comments/account-comments.component';
import { AccountGroupsComponent } from './components/account-selected-components/account-groups/account-groups.component';
import { AuthGuard } from 'src/app/services/auth-guard.service';

const accountsRoutes: Routes = [
    {
        path: 'accounts', component: AccountContainerComponent, canActivate: [AuthGuard], children: [
            {path: ':id', component: AccountComponentComponent, children: [
                { path: 'information', component: AccountInformationComponent },
                { path: 'projects', component: AccountProjectsComponent },
                { path: 'parts', component: AccountPartsComponent },
                { path: 'comments', component: AccountCommentsComponent },
                { path: 'groups', component: AccountGroupsComponent }
            ]
        }]
    }
]
@NgModule({
    imports: [RouterModule.forChild(accountsRoutes)],
    exports: [RouterModule]
})
export class AccountRoutingModule { }