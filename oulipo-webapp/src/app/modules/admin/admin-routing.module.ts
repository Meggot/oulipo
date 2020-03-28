import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminContainerComponent } from './admin-container/admin-container.component';
import { AuditContainerComponent } from './audit-container/audit-container.component';
import { AuthGuard } from 'src/app/services/auth-guard.service';
import { AuthAdminGuard } from 'src/app/services/auth-guard-admin.service';
import { AuditSelectedComponent } from './audit-container/components/audit/audit-selected/audit-selected.component';

const adminRoutes: Routes = [
    {
        path: 'admin', component: AdminContainerComponent, canActivate: [AuthGuard], children: [
            {
                path: 'audits', component: AuditContainerComponent, children: [
                    { path: ':id', component: AuditSelectedComponent }
                ]
            },
        ]
    }]
@NgModule({
    imports: [RouterModule.forChild(adminRoutes)],
    exports: [RouterModule]
})
export class AdminRoutingModule {}