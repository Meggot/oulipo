import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ProjectModel } from '../../../shared/models/project.model';
import { ActivatedRoute, Params, ParamMap } from '@angular/router';
import { Store } from '@ngrx/store';
import * as ProjectState from '../../../projects/store/projects.reducers';
import { map, mergeMap, tap, flatMap } from 'rxjs/operators';
import * as ProjectActions from '../../../projects/store/project.actions';
import { Observable } from 'rxjs';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
import { PartModel } from 'src/app/modules/shared/models/part.model';
import * as NotificationActions from '../../../notification/store/notification.actions'
import { PostboxModel } from 'src/app/modules/shared/models/postbox.model';
import { AppState } from 'src/app/store/app.reducers';
@Component({
  selector: 'app-project-selected',
  templateUrl: './project-selected.component.html',
  styleUrls: ['./project-selected.component.css']
})
export class ProjectSelectedComponent implements OnInit {

  ngOnInit(): void {
  }

  thisAccount: Observable<AccountModel>
  viewingProject: Observable<ProjectModel>
  requestPartErrorMessage: Observable<string>
  editErrorMessage: Observable<string>
  showingDashboard = false;
  id: number;

  constructor(private store: Store<AppState>,
    private activatedRoute: ActivatedRoute) {
    this.requestPartErrorMessage = this.store.select('projects', 'requestPartErrorMessage')
    this.thisAccount = this.store.select('accounts', 'thisAccount')
    this.editErrorMessage = this.store.select('projects', 'editErrorMessage');
    this.viewingProject = store.select('projects').pipe(
      map((state: ProjectState.State) => {
        return state.viewingProject;
      })
    )
    this.activatedRoute.paramMap.subscribe(
      (param: ParamMap) => {
        if (param.has('id')) {
          this.id = +param.get('id');
          this.store.dispatch(new ProjectActions.LoadandSetViewingProjectById(this.id))
          this.store.dispatch(new NotificationActions.SubscribeOnIdTypes({
            entityId: this.id,
            types: [
              "INBOX_MESSAGE_RECEIVED",
              "PROJECT_TAG_CREATED",
              "PROJECT_TAG_REMOVED",
              "PROJECT_PART_POSTED",
              "PROJECT_PART_UPDATED",
              "PROJECT_EDIT_POSTED",
              "PROJECT_EDIT_UPDATED",
              "AUTHOR_PART_UPDATED",
              "AUTHOR_ROLE_UPDATED"
            ]
          })
          )}
      }
    )
    }
          

  onForceDeletePart(part: PartModel) {
    this.store.dispatch(new ProjectActions.DeletePart(part.idField));
  }

  toggleDashboard() {
    this.showingDashboard = !this.showingDashboard;
  }

  onRequestPart() {
    this.store.dispatch(new ProjectActions.RequestPartOnProjectId(this.id))
  }

}
