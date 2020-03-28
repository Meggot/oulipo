import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InboxContainerComponent } from 'src/app/modules/inbox/inbox-container/inbox-container.component';
import { AuthGuard } from 'src/app/services/auth-guard.service';

const inboxRoutes: Routes = [
    {
        path: 'inbox', component: InboxContainerComponent, canActivate: [AuthGuard], children: [
          {path: ':username', component: InboxContainerComponent}
        ]
      },
]
@NgModule({
    imports: [RouterModule.forChild(inboxRoutes)],
    exports: [RouterModule]
})
export class InboxRoutingModule {}