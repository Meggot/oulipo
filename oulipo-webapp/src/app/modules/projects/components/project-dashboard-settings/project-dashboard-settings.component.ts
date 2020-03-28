import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { ProjectService } from 'src/app/services/project.service';
import { ProjectModel } from 'src/app/modules/shared/models/project.model';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';
import * as ProjectAction from '../../store/project.actions'
@Component({
  selector: 'app-project-dashboard-settings',
  templateUrl: './project-dashboard-settings.component.html',
  styleUrls: ['./project-dashboard-settings.component.css']
})
export class ProjectDashboardSettingsComponent {

  viewingProject: Observable<ProjectModel>
  
  constructor(private store: Store<AppState>) { 
    this.viewingProject = store.select('projects', 'viewingProject')
  }

  ngOnInit() {
  }
  
  addTag(tagValue: string) {
    let sub = this.viewingProject.subscribe(
      (project: ProjectModel) => {
        this.store.dispatch(new ProjectAction.PostTagValueOnProject({projectId: project.projectId, value: tagValue}))
      }
    )
    sub.unsubscribe();
  }

  removeTag($event) {
  }
}
