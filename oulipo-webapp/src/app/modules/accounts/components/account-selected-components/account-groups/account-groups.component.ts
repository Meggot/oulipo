import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import * as AccountState from '../../../store/account.reducers';
import { Observable } from 'rxjs';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
import { map } from 'rxjs/operators';
import { AppState } from 'src/app/store/app.reducers';


@Component({
  selector: 'app-account-groups',
  templateUrl: './account-groups.component.html',
  styleUrls: ['./account-groups.component.css']
})
export class AccountGroupsComponent {

  public accountState: Observable<AccountState.State>
  public viewingAccount: Observable<AccountModel>;

  constructor(public store: Store<AppState>) {
    this.accountState = store.select('accounts');
    this.viewingAccount = this.accountState.pipe(
      map((state: AccountState.State) => {
          return state.viewingAccount;
      })
    )
  }

}