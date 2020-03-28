import { Component, OnInit } from '@angular/core';
import { ProjectService } from '../../../services/project.service';
import { Subscription, Observable } from 'rxjs';
import { ProjectModel } from '../../shared/models/project.model';
import { HateosResponseModel } from '../../shared/models/hateos-response.model';
import { Router } from '@angular/router';
import { HttpParams, HttpHeaders, HttpClient } from '@angular/common/http';
import { SessionService } from 'src/app/services/session.service';
import { Store, State } from '@ngrx/store';
import * as AppState from '../../../store/app.reducers'
import * as fromProjects from '../../projects/store/projects.reducers'
import * as fromExplorer from '../../explore/store/explorer.reducers'
import * as ExplorerActions from '../../explore/store/explorer.actions'
import { ExplorerSearchQuery } from '../../shared/models/explorer-search-query.model';
import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { map, take } from 'rxjs/operators';
import { AccountModel } from '../../shared/models/account.model';

@Component({
  selector: 'app-explore-container',
  templateUrl: './explore-container.component.html',
  styleUrls: ['./explore-container.component.css']
})
export class ExploreContainerComponent implements OnInit {

  activePage: Observable<ProjectModel[]>
  pageLinks: Observable<PaginationLinks>
  pageData: Observable<PaginationData>
  panelView = false;

  constructor(private store: Store<fromExplorer.State>,
    private router: Router) {
  }

  onToggleView() {
    this.panelView = !this.panelView;
  }

  ngOnInit() {
    this.store.dispatch(new ExplorerActions.Search(new ExplorerSearchQuery()))
    this.pageLinks = this.store.select('explorer','pageLinks').pipe(
      map((pageLinks: PaginationLinks) => {
        return pageLinks;
      })
    );
    this.pageData = this.store.select('explorer','pageData').pipe(
      map((pageData: PaginationData) => {
        return pageData;
      })
    );
    this.activePage = this.store.select('explorer','activePage').pipe(
      map((activePage: ProjectModel[]) => {
        return activePage;
      })
    );
  }

    search(exploreSearchForm: ExplorerSearchQuery) {
      this.store.dispatch(new ExplorerActions.Search(exploreSearchForm))
    }

    navigateToProjectDetail(id: number) {
      this.router.navigate(['projects', id])
    }

    onNavigatePageOption(page: string) {
     let sub = this.pageLinks.subscribe(
        (pageLinks) => {
          switch(page) {
            case 'NEXT':
              this.store.dispatch(new ExplorerActions.NavigatePage(pageLinks.next.href));
              return;
            case 'PREVIOUS':
              this.store.dispatch(new ExplorerActions.NavigatePage(pageLinks.prev.href));
              return;
          }
        }
      )
      sub.unsubscribe();
    }
}
