import {Component, OnInit, ApplicationInitStatus} from '@angular/core';
import {ProjectModel} from '../../../shared/models/project.model';
import {SessionService} from '../../../../services/session.service';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import * as fromAuth from '../../../auth/store/auth.reducers'
import * as fromAccounts from '../../../accounts/store/account.reducers'
import * as fromProjects from '../../../projects/store/projects.reducers'
import * as AppActions from '../../../core/components/app.actions'
import { AppState } from 'src/app/store/app.reducers';
import { Router } from '@angular/router';
import { AccountModel } from 'src/app/modules/shared/models/account.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public sessionService: SessionService,
              public store: Store<AppState>,
              public router: Router) {
  }

  authState: Observable<fromAuth.State>;
  accountState: Observable<fromAccounts.State>;
  projectsState: Observable<fromProjects.State>;
  projectsSelected = false;

  thisAccount: Observable<AccountModel>;

  authoredProjects: ProjectModel[];

  ngOnInit() {
    this.authState = this.store.select('auth')
    this.accountState = this.store.select('accounts');
    this.projectsState = this.store.select('projects');
    this.thisAccount = this.store.select('accounts', 'thisAccount')
  }

  clickProjects() {
    this.projectsSelected = !this.projectsSelected;
  }

  logout() {
    this.store.dispatch(new AppActions.Logout());
  }

  navToAccount(account: AccountModel) {
    this.router.navigate(['accounts', account.idField])
  }
}
