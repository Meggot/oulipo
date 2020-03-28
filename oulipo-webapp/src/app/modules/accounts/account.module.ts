import { NgModule } from '@angular/core';
import { AccountCommentsComponent } from './components/account-selected-components/account-comments/account-comments.component';
import { AccountContainerComponent } from './account-container/account-container.component';
import { AccountComponentComponent } from './components/account-component/account-component.component';
import { AccountGroupsComponent } from './components/account-selected-components/account-groups/account-groups.component';
import { AccountProjectsComponent } from './components/account-selected-components/account-projects/account-projects.component';
import { AccountPartsComponent } from './components/account-selected-components/account-parts/account-parts.component';
import { AccountInformationComponent } from './components/account-selected-components/account-information/account-information.component';
import { AccountRoutingModule } from './account-routing.module';
import { SharedModule } from '../shared/shared.module';
import { AccountApi } from './store/account.api';

@NgModule({
  declarations: [    
    AccountCommentsComponent,
    AccountContainerComponent,
    AccountComponentComponent,
    AccountGroupsComponent,
    AccountProjectsComponent,
    AccountPartsComponent,
    AccountInformationComponent
  ],
  imports: [
    AccountRoutingModule,
    SharedModule
  ],
  providers: [
    AccountApi
  ]
})
export class AccountModule { }
