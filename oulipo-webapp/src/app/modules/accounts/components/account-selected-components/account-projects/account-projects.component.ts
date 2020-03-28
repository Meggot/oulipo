import { Component, OnInit } from '@angular/core';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AuthorModel } from 'src/app/modules/shared/models/author.model';
import { map } from 'rxjs/operators';
import * as ProjectState from '../../../../projects/store/projects.reducers';
import * as AccountState from '../../../store/account.reducers';
import { Router } from '@angular/router';

@Component({
  selector: 'app-account-projects',
  templateUrl: './account-projects.component.html',
  styleUrls: ['./account-projects.component.css']
})
export class AccountProjectsComponent implements OnInit{

  public viewingAccount: Observable<AccountModel>;
  public viewingAuthor: Observable<AuthorModel>;

  constructor(public store: Store<AppState>,
    private router: Router) {}

  ngOnInit() {
    this.viewingAccount = this.store.select('accounts').pipe(
      map((state: AccountState.State) => {
        console.log(state);
        return state.viewingAccount;
      })
    )
    this.viewingAuthor = this.store.select('projects').pipe(
      map((state: ProjectState.State) => {
        console.log(state);
        return state.viewingAuthor;
      })
    )
  }

  navigateToProjectDetail(projectId: number) {
    this.router.navigate(['projects', projectId])
  }
}