import { NgModule } from '@angular/core';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminContainerComponent } from './admin-container/admin-container.component';
import { AuditSelectedComponent } from './audit-container/components/audit/audit-selected/audit-selected.component';
import { AuditContainerComponent } from './audit-container/audit-container.component';
import { SharedModule } from '../shared/shared.module'
import { AdminApi } from './store/admin.api';
@NgModule({
    declarations: [
        AuditSelectedComponent,
        AuditContainerComponent,
        AdminContainerComponent
    ],
    imports: [
        AdminRoutingModule,
        SharedModule
    ],
    providers: [
        AdminApi
    ]
})
export class AdminModule {

}