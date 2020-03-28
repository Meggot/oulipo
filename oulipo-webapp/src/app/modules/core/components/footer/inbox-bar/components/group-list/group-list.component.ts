import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupModel } from 'src/app/modules/shared/models/group.model';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';
import { AccountModel } from 'src/app/modules/shared/models/account.model';

import * as GroupActions from '../../../../../../groups/store/groups.actions';
import { Router } from '@angular/router';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {
  
  groups: Observable<GroupModel[]>

  currentMessage: Observable<string>

  thisAccount: Observable<AccountModel>
  
  groupSelected = false;

  @ViewChild('searchText')
  searchText: ElementRef;

  constructor(private store: Store<AppState>,
    private router: Router) {
    this.groups = this.store.select('groups', 'groups');
    this.currentMessage = this.store.select('groups', 'message')
    this.thisAccount = this.store.select('accounts', 'thisAccount');
   }

  ngOnInit() {

  }

  joinGroup() {

  }

  setSearchForm(group: GroupModel) {
    console.log(group)
    this.searchText.nativeElement.value = group.name;
    this.groupSelected = true;
    let sub = this.thisAccount.subscribe(
      (thisAccount: AccountModel) => {
        this.store.dispatch(new GroupActions.PostGroupMembership(
          {groupId: group.idField, username: thisAccount.username, role: 'MEMBER'}))
      }
    )
    sub.unsubscribe();
  }

  clearGroupSelected() {
    this.groupSelected = false
  }

  navigateToGroup(id: number) {
    this.router.navigate((['groups', id]))
  }
}
