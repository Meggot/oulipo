import { RouterModule, Routes, PreloadAllModules } from '@angular/router';
import { NgModule } from '@angular/core';
import { ErrorContainerComponent } from './modules/core/error-container/error-container.component';
import { HomeContainerComponent } from './modules/core/home-container/home-container.component';
import { AuthGuard } from './services/auth-guard.service';

const appRoutes: Routes = [
  { path: '', component: HomeContainerComponent, pathMatch: 'full'},
//   { path: 'explore', canLoad: [AuthGuard], loadChildren: './modules/explore/explore.module#ExploreModule', data: { preload: true }
// },
  // { path: 'accounts', canLoad: [AuthGuard], loadChildren: './modules/accounts/account.module#AccountModule'},
  { path: 'not-logged-in', component: ErrorContainerComponent },
  // { path: '**', component: ErrorContainerComponent }
];
@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
