import { NgModule } from '@angular/core';
import { ProjectCreateFormComponent } from 'src/app/modules/projects/components/project-create-form/project-create-form.component';
import { ProjectSelectedComponent } from 'src/app/modules/projects/components/project-selected/project-selected.component';
import { ProjectDashboardCollaboratorsComponent } from 'src/app/modules/projects/components/project-dashboard-collaborators/project-dashboard-collaborators.component';
import { ProjectDashboardComponent } from 'src/app/modules/projects/components/project-selected/project-dashboard/project-dashboard.component';
import { ProjectDashboardPartsComponent } from 'src/app/modules/projects/components/project-dashboard-parts/project-dashboard-parts.component';
import { ProjectDashboardRecentComponent } from 'src/app/modules/projects/components/project-dashboard-recent/project-dashboard-recent.component';
import { ProjectDashboardSettingsComponent } from 'src/app/modules/projects/components/project-dashboard-settings/project-dashboard-settings.component';
import { ProjectPartComponent } from './components/project-selected/project-part/project-part.component';
import { ProjectCopyComponent } from './components/project-selected/project-copy/project-copy.component';
import { ProjectContainerComponent } from './project-container/project-container.component';
import { ProjectsRoutingModule } from './projects-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ProjectRoleItemComponent } from './components/project-selected/project-roles-list/project-role-item/project-role-item.component';
import { ProjectRolesListComponent } from './components/project-selected/project-roles-list/project-roles-list.component';
import { ProjectApi } from './store/project.api';
import { ProjectDashboardEditsComponent } from './components/project-dashboard-edits/project-dashboard-edits.component';

@NgModule({
    declarations: [
        ProjectContainerComponent,
        ProjectCreateFormComponent,
        ProjectSelectedComponent,
        ProjectDashboardComponent,
        ProjectDashboardCollaboratorsComponent,
        ProjectDashboardPartsComponent,
        ProjectDashboardRecentComponent,
        ProjectDashboardSettingsComponent,
        ProjectRoleItemComponent,
        ProjectRolesListComponent,
        ProjectPartComponent,
        ProjectCopyComponent,
        ProjectDashboardEditsComponent
    ],
    imports: [
        ProjectsRoutingModule,
        SharedModule
    ],
    providers: [
        ProjectApi
    ]

})
export class ProjectsModule {}