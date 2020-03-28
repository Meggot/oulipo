import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import * as fromAccounts from '../../../store/account.reducers';
import * as fromApp from '../../../../../store/app.reducers'
import { Observable } from 'rxjs';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-account-comments',
  templateUrl: './account-comments.component.html',
  styleUrls: ['./account-comments.component.css']
})
export class AccountCommentsComponent {
  
  private accountsState: Observable<fromAccounts.State>
  public viewingAccount: Observable<AccountModel>;

  constructor(private store: Store<fromApp.AppState>,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.accountsState = this.store.select('accounts');
    this.viewingAccount = this.accountsState.pipe(
      map((state: fromAccounts.State) => {
          return state.viewingAccount;
      })
    )
  }
}