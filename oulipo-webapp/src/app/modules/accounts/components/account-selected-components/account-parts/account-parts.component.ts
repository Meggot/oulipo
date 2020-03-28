import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { map } from 'rxjs/operators';
import * as AccountState from '../../../store/account.reducers'
import * as ProjectState from '../../../../projects/store/projects.reducers'
import { AuthorModel } from 'src/app/modules/shared/models/author.model';
import { AppState } from 'src/app/store/app.reducers';


@Component({
  selector: 'app-account-parts',
  templateUrl: './account-parts.component.html',
  styleUrls: ['./account-parts.component.css']
})
export class AccountPartsComponent {

  public accountState: Observable<AccountState.State>
  public viewingAccount: Observable<AccountModel>;
  public viewingAuthor: Observable<AuthorModel>;

  constructor(public store: Store<AppState>) {
    this.accountState = store.select('accounts');
    this.viewingAccount = this.accountState.pipe(
      map((state: AccountState.State) => {
        console.log(state);
          return state.viewingAccount;
      })
    )
    this.viewingAuthor = store.select('projects').pipe(
      map((state: ProjectState.State) => {
        return state.viewingAuthor;
      }) 
    )
  }
}