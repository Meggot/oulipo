import { NgModule } from '@angular/core';
import { ExploreContainerComponent } from 'src/app/modules/explore/explore-container/explore-container.component';
import { ExploreSearchDashboardComponent } from 'src/app/modules/explore/components/explore-search-dashboard/explore-search-dashboard.component';
import { SharedModule } from '../shared/shared.module';
import { ExploreRoutingModule } from './explore-routing.module';
import { ExplorerApi } from './store/explorer.api';

@NgModule({
    declarations: [
        ExploreContainerComponent,
        ExploreSearchDashboardComponent
    ],
    imports: [
        ExploreRoutingModule,
        SharedModule
    ],
    providers: [
        ExplorerApi
    ]
})
export class ExploreModule {}