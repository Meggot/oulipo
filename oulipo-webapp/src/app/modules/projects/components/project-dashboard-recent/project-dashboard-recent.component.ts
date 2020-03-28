import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';
import { Observable } from 'rxjs';
import { PaginationData } from 'src/app/modules/shared/models/pagination-data.model';
import { PaginationLinks } from 'src/app/modules/shared/models/pagination-links.model';
import { AuditModel } from 'src/app/modules/shared/models/audit.model';
import { ProjectModel } from 'src/app/modules/shared/models/project.model';
@Component({
  selector: 'app-project-dashboard-recent',
  templateUrl: './project-dashboard-recent.component.html',
  styleUrls: ['./project-dashboard-recent.component.css']
})
export class ProjectDashboardRecentComponent implements OnInit {

  message: Observable<string>;
  pageData: Observable<PaginationData>;
  pageLinks: Observable<PaginationLinks>
  page: Observable<AuditModel[]>
  viewingProject: Observable<ProjectModel>

  constructor(private store: Store<AppState>) {
    this.message = store.select('projects', 'projectHistoryMessage');
    this.pageData = store.select('projects', 'projectHistoryPaginationData');
    this.pageLinks = store.select('projects', 'projectPaginationLinks');
    this.page = store.select('projects', 'projectHistoryPage')
    this.viewingProject = store.select('accounts', 'viewingProject');
  }

  ngOnInit() {

  }

}
