import { Routes, RouterModule } from '@angular/router';

import { NgModule } from '@angular/core';

import { ProjectContainerComponent } from './project-container/project-container.component';

import { ProjectCreateFormComponent } from 'src/app/modules/projects/components/project-create-form/project-create-form.component';

import { ProjectSelectedComponent } from 'src/app/modules/projects/components/project-selected/project-selected.component';

import { ProjectDashboardRecentComponent } from 'src/app/modules/projects/components/project-dashboard-recent/project-dashboard-recent.component';

import { ProjectDashboardPartsComponent } from 'src/app/modules/projects/components/project-dashboard-parts/project-dashboard-parts.component';

import { ProjectDashboardCollaboratorsComponent } from 'src/app/modules/projects/components/project-dashboard-collaborators/project-dashboard-collaborators.component';

import { ProjectDashboardSettingsComponent } from 'src/app/modules/projects/components/project-dashboard-settings/project-dashboard-settings.component';
import { AuthGuard } from 'src/app/services/auth-guard.service';
import { ProjectDashboardEditsComponent } from './components/project-dashboard-edits/project-dashboard-edits.component';

const projectRoutes: Routes = [
    {
        path: 'projects', component: ProjectContainerComponent, canActivate: [AuthGuard], children: [
          { path: 'new', component: ProjectCreateFormComponent },
          { path: ':id', component: ProjectSelectedComponent, children: [
              { path: 'recent', component: ProjectDashboardRecentComponent },
              { path: 'parts', component: ProjectDashboardPartsComponent },
              { path: 'collaborators', component: ProjectDashboardCollaboratorsComponent },
              { path: 'settings', component: ProjectDashboardSettingsComponent },
              { path: 'edits', component: ProjectDashboardEditsComponent }
            ]
          }
        ]
      }
]
@NgModule({
    imports: [RouterModule.forChild(projectRoutes)],
    exports: [RouterModule]
})
export class ProjectsRoutingModule {}