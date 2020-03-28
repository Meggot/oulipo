import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ProjectModel } from 'src/app/modules/shared/models/project.model';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
import * as ProjectActions from '../../store/project.actions';
import { ProjectRolesModel } from 'src/app/modules/shared/models/project-roles.model';

@Component({
  selector: 'app-project-dashboard-collaborators',
  templateUrl: './project-dashboard-collaborators.component.html',
  styleUrls: ['./project-dashboard-collaborators.component.css']
})
export class ProjectDashboardCollaboratorsComponent implements OnInit {

  @ViewChild('searchText')
  searchText: ElementRef
  accountSelected = false;

  viewingProject: Observable<ProjectModel>
  postRoleMessage: Observable<string>
  thisAccount: Observable<AccountModel>

  constructor(private store: Store<AppState>) {
    this.viewingProject = store.select('projects', 'viewingProject')
    this.postRoleMessage = store.select('projects', 'postRoleErrorMessage')
    this.thisAccount = store.select('accounts', 'thisAccount')
  }

  ngOnInit() {
  }

  setSearchForm(account: AccountModel) {
    console.log(this.searchText);
    this.searchText.nativeElement.value = account.username;
    this.accountSelected = true;
  }

  clearAccountSelected(event: any) {
    this.accountSelected = false;
  }

  onInviteCollaborator() {
    let username = this.searchText.nativeElement.value;
    let foundAccountSub = this.store.select('accounts', 'searchResults').subscribe(
      (accounts: AccountModel[]) => {
        let foundAccount = accounts.find(account => account.username === username);
        if (foundAccount !== null) {
          let projectSub = this.viewingProject.subscribe(
            (project: ProjectModel) => {
              this.store.dispatch(new ProjectActions.PostRoleOnProject(
                {
                  projectId: project.projectId,
                  userId: foundAccount.idField,
                  role: 'CONTRIBUTOR'
                }))
            }
          )
          projectSub.unsubscribe();
        } else {
          this.store.dispatch(new ProjectActions.PostRoleOnProjectFail("That user does not exist."))
        }
      }
    )
    foundAccountSub.unsubscribe();
  }

  onUpdateRole(role: ProjectRolesModel, roleUpdate: string) {
    this.store.dispatch(new ProjectActions.PatchRoleOnProject({ roleId: role.idField, newRole: roleUpdate }))
  }

}