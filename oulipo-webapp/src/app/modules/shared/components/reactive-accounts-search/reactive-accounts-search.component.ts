import { Component, OnInit, Input, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import * as AccountActions from '../../../accounts/store/account.actions'
import { stringify } from '@angular/core/src/render3/util';
import { LowerCasePipe } from '@angular/common';
@Component({
  selector: 'app-reactive-accounts-search',
  templateUrl: './reactive-accounts-search.component.html',
  styleUrls: ['./reactive-accounts-search.component.css']
})
export class ReactiveAccountsSearchComponent implements OnInit {

  @Input()
  searchForm: string;
  @Output()
  foundAccount: EventEmitter<AccountModel>

  accountsFound: AccountModel[];

  constructor(private store: Store<AppState>) {
    this.foundAccount = new EventEmitter();
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const searchTerm = this.searchForm;
    if (searchTerm.length >= 3) {
      this.store.dispatch(new AccountActions.SearchAccounts(searchTerm));
      let sub = this.store.select('accounts', 'searchResults').subscribe(
        (accountsFound: AccountModel[]) => {
          this.accountsFound = accountsFound.filter(account => account.username.indexOf(searchTerm) > -1);
        }
      )
      sub.unsubscribe();
    } else {
      this.clear();
    }
  }

  setAccountFound(account: AccountModel){
    console.log('emit!')
    this.foundAccount.emit(account);
    this.searchForm = '';
  }

  clear() {
    this.accountsFound = [];
  }
}