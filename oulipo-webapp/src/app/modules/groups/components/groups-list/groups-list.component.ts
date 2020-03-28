import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupModel } from 'src/app/modules/shared/models/group.model';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';
@Component({
  selector: 'app-groups-list',
  templateUrl: './groups-list.component.html',
  styleUrls: ['./groups-list.component.css']
})
export class GroupsListComponent implements OnInit {

  public groups: Observable<GroupModel[]>

  constructor(private store: Store<AppState>) {
    this.groups = this.store.select('groups', 'groups');
   }

  ngOnInit() {

  }

}
