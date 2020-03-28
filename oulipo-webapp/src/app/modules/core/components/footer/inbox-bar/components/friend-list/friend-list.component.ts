import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
import { Observable } from 'rxjs';
import * as AccountActions from '../../../../../../accounts/store/account.actions'
import { Router } from '@angular/router';
@Component({
  selector: 'app-friend-list',
  templateUrl: './friend-list.component.html',
  styleUrls: ['./friend-list.component.css']
})
export class FriendListComponent implements OnInit {

  thisAccount: Observable<AccountModel>;

  @ViewChild('searchText')
  searchText: ElementRef
  accountSelected = false;
  account: AccountModel;
  currentMessage: string;
  message: Observable<string>

  constructor(private store: Store<AppState>,
    private router: Router) { 
    store.select('accounts', 'message').subscribe(
      (message: string) => {
        console.log(message);
        this.currentMessage = message;
      }
    )
  }

  ngOnInit() {
    this.thisAccount = this.store.select('accounts', 'thisAccount');
  }

  actionOnRelationship(id: number, action: string) {
    console.log(status);
    this.store.dispatch(new AccountActions.ActionOnRelationship({id, action}));
  }

  navigateToAccount(id: number) {
    this.router.navigate(['accounts', id])
  }

  setSearchForm(account: AccountModel) {
    console.log(this.searchText);
    this.searchText.nativeElement.value = account.username;
    this.accountSelected = true;
  }

  clearAccountSelected(event: any) {
    this.accountSelected = false;
  }


  requestFriend() {
    let sub = this.store.select('accounts', 'searchResults').subscribe(
      (accounts: AccountModel[]) => {
        let account = accounts.find(account => account.username === this.searchText.nativeElement.value)
        if (account !== null) {
          console.log(account);
          this.store.dispatch(new AccountActions.RequestRelationship({type: "FRIEND", username: this.searchText.nativeElement.value}))
        } else {
          console.log('error!')
        }
      }
    )
    sub.unsubscribe();
  }


}
