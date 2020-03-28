import { GroupsContainerComponent } from "./groups-container/groups-container.component";
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from 'src/app/services/auth-guard.service';
import { GroupCreateComponent } from './components/group-create/group-create.component';
import { GroupSelectedComponent } from './components/group-selected/group-selected.component';
import { NgModule } from '@angular/core';
import { GroupsListComponent } from './components/groups-list/groups-list.component';

const groupsRoutes: Routes = [
    {
        path: 'groups', component: GroupsContainerComponent, canActivate: [AuthGuard], children: [
            { path: '', component: GroupsListComponent, pathMatch: 'full'},
            { path: 'new', component: GroupCreateComponent },
            { path: ':id', component: GroupSelectedComponent }
        ]
    }
]
@NgModule({
    imports: [RouterModule.forChild(groupsRoutes)],
    exports: [RouterModule]
})
export class GroupsRoutingModule {}