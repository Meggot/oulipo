import { Routes, RouterModule } from "@angular/router";

import { LoginContainerComponent } from "src/app/modules/auth/login-container/login-container.component";

import { LoginComponent } from "src/app/modules/auth/components/login/login.component";

import { RegisterComponent } from "src/app/modules/auth/components/register/register.component";
import { NgModule } from '@angular/core';

const authRoutes: Routes = [
    {
        path: 'auth', component: LoginContainerComponent, children: [
          {path: 'login', component: LoginComponent},
          {path: 'register', component: RegisterComponent}
        ]
      },
]
@NgModule({
    imports: [RouterModule.forChild(authRoutes)],
    exports: [RouterModule]
})
export class AuthRoutingModule{}