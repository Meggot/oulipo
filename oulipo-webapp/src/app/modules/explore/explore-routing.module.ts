import { Routes, RouterModule } from '@angular/router';
import { ExploreContainerComponent } from 'src/app/modules/explore/explore-container/explore-container.component';
import { NgModule } from '@angular/core';

const exploreRoutes: Routes = [
    {
        path: 'explore', component: ExploreContainerComponent
    }
]
@NgModule({
    imports: [RouterModule.forChild(exploreRoutes)],
    exports: [RouterModule]
})
export class ExploreRoutingModule {

}