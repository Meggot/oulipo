import { Component, OnInit, Input, Output, EventEmitter, SimpleChanges } from '@angular/core';
import { GroupModel } from '../../models/group.model';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';
import { AccountModel } from '../../models/account.model';
import * as GroupActions from '../../../groups/store/groups.actions'
@Component({
  selector: 'app-reactive-groups-search',
  templateUrl: './reactive-groups-search.component.html',
  styleUrls: ['./reactive-groups-search.component.css']
})
export class ReactiveGroupsSearchComponent implements OnInit {

  @Input()
  searchForm: string;
  @Output()
  foundGroup: EventEmitter<GroupModel>

  groupsFound: GroupModel[];

  constructor(private store: Store<AppState>) {
    this.foundGroup = new EventEmitter();
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const searchTerm = this.searchForm;
    if (searchTerm.length >= 3) {
      this.store.dispatch(new GroupActions.SearchGroups(searchTerm));
      let sub = this.store.select('groups', 'groupSearchResults').subscribe(
        (groupsFound: GroupModel[]) => {
          this.groupsFound = groupsFound.filter(group => group.name.indexOf(searchTerm) > -1);
        }
      )
      sub.unsubscribe();
    } else {
      this.clear();
    }
  }

  setGroupFound(group: GroupModel){
    console.log('emit!')
    this.foundGroup.emit(group);
    this.searchForm = '';
  }

  clear() {
    this.groupsFound = [];
  }
}
