import { LoginContainerComponent } from './login-container/login-container.component';
import { RegisterComponent } from './components/register/register.component';
import { NgModule } from '@angular/core';
import { LoginComponent } from './components/login/login.component';
import { AuthRoutingModule } from './auth-routing.module';
import { SharedModule } from '../shared/shared.module';
import { AuthApi } from './store/auth.api';

@NgModule({
    declarations: [
        LoginContainerComponent,
        LoginComponent,
        RegisterComponent,
    ],
    imports: [
        AuthRoutingModule,
        SharedModule
    ],
    providers: [
        AuthApi
    ]

})
export class AuthModule {

}