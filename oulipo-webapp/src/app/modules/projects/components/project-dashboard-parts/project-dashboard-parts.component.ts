import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';
import { Observable } from 'rxjs';
import { ProjectModel } from 'src/app/modules/shared/models/project.model';

@Component({
  selector: 'app-project-dashboard-parts',
  templateUrl: './project-dashboard-parts.component.html',
  styleUrls: ['./project-dashboard-parts.component.css']
})
export class ProjectDashboardPartsComponent implements OnInit {

  viewingProject: Observable<ProjectModel>
  
  constructor(private store: Store<AppState>) { 
    this.viewingProject = store.select('projects', 'viewingProject')
  }

  ngOnInit() {
  }

}
