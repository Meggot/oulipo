import { Component, OnInit } from '@angular/core';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { GroupModel } from '../../shared/models/group.model';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-groups-container',
  templateUrl: './groups-container.component.html',
  styleUrls: ['./groups-container.component.css']
})
export class GroupsContainerComponent {

}
