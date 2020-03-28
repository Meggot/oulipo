import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { GroupsContainerComponent } from './groups-container/groups-container.component';
import { GroupSelectedComponent } from './components/group-selected/group-selected.component';
import { GroupCreateComponent } from './components/group-create/group-create.component';
import { GroupsRoutingModule } from './groups-routing.module';
import { GroupsApi } from './store/groups.api';
import { GroupsListComponent } from './components/groups-list/groups-list.component';

@NgModule({
  declarations: [
    GroupsContainerComponent,
    GroupSelectedComponent,
    GroupCreateComponent,
    GroupsListComponent
  ],
  imports: [
    GroupsRoutingModule,
    SharedModule
  ],
  providers: [
    GroupsApi
  ]
})
export class GroupsModule { }
