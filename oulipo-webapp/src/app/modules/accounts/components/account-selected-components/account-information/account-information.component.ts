import { Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';
import { Observable } from 'rxjs';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
@Component({
  selector: 'app-account-information',
  templateUrl: './account-information.component.html',
  styleUrls: ['./account-information.component.css']
})
export class AccountInformationComponent {


  public viewingAccount: Observable<AccountModel>;

  constructor(public store: Store<AppState>) {
    this.viewingAccount = store.select('accounts', 'viewingAccount');
  }

}
