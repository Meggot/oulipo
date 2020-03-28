import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { GroupModel } from 'src/app/modules/shared/models/group.model';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import * as GroupActions from '../../store/groups.actions'
@Component({
  selector: 'app-group-selected',
  templateUrl: './group-selected.component.html',
  styleUrls: ['./group-selected.component.css']
})
export class GroupSelectedComponent implements OnInit {

  viewingGroup: Observable<GroupModel>

  constructor(private store: Store<AppState>,
    private activatedRoute: ActivatedRoute,
    private router: Router) {
    this.viewingGroup = this.store.select('groups', 'viewingGroup');
    this.activatedRoute.paramMap.subscribe(
      (param: ParamMap) => {
        if (param.has('id')) {
          this.store.dispatch(new GroupActions.LoadAndSetViewingGroup(+param.get('id')))
        }
      }
    )
  }
  
  ngOnInit() {

  }

  navigateToAccount(id: number) {
    this.router.navigate(['accounts', id])
  }
}