import { Component, OnInit } from '@angular/core';
import { AccountModel } from '../../../shared/models/account.model';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import * as fromApp from '../../../../store/app.reducers';
import * as fromAccounts from '../../store/account.reducers';
import * as AccountActions from '../../store/account.actions';
import * as ProjectActions from '../../../projects/store/project.actions';
import * as AppActions from '../../../core/components/app.actions'

import { map } from 'rxjs/operators';
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-account-component',
  templateUrl: './account-component.component.html',
  styleUrls: ['./account-component.component.css']
})
export class AccountComponentComponent implements OnInit {

  public accountsState: Observable<fromAccounts.State>
  public viewingAccount: Observable<AccountModel>;

  constructor(private store: Store<fromApp.AppState>,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.accountsState = this.store.select('accounts');
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        if (params.has('id')) {
          this.store.dispatch(new AppActions.SelectUser(+params.get('id')));
        }
      }
    );
    this.viewingAccount = this.accountsState.pipe(
      map((state: fromAccounts.State) => {
        return state.viewingAccount;
      })
    )
  }

}
