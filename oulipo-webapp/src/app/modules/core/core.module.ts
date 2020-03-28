import { NgModule } from "@angular/core";
import { HeaderComponent } from 'src/app/modules/core/components/header/header.component';
import { FooterComponent } from 'src/app/modules/core/components/footer/footer.component';
import { HomeContainerComponent } from 'src/app/modules/core/home-container/home-container.component';
import { ErrorContainerComponent } from 'src/app/modules/core/error-container/error-container.component';
import { SharedModule } from '../shared/shared.module';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { ProjectService } from 'src/app/services/project.service';
import { SessionService } from 'src/app/services/session.service';
import { MatrixService } from 'src/app/services/matrix.service';
import { AuthGuard } from 'src/app/services/auth-guard.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from 'src/app/services/auth.interceptor';
import { InboxBarComponent } from './components/footer/inbox-bar/inbox-bar.component';
import { GroupListComponent } from './components/footer/inbox-bar/components/group-list/group-list.component';
import { FriendListComponent } from './components/footer/inbox-bar/components/friend-list/friend-list.component';

@NgModule({
    declarations: [
        HeaderComponent,
        FooterComponent,
        HomeContainerComponent,
        ErrorContainerComponent,
        InboxBarComponent,
        GroupListComponent,
        FriendListComponent,
    ],
    imports: [
        SharedModule,
        AppRoutingModule
    ],
    exports: [
        HomeContainerComponent,
        AppRoutingModule,
        HeaderComponent,
        FooterComponent
    ],
    providers: [
        ProjectService, 
        SessionService, 
        MatrixService, 
        AuthGuard,
        {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
    ]
})
export class CoreModule {}