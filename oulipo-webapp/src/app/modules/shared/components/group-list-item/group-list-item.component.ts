import { Component, OnInit, Input } from '@angular/core';
import { GroupModel } from '../../models/group.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-group-list-item',
  templateUrl: './group-list-item.component.html',
  styleUrls: ['./group-list-item.component.css']
})
export class GroupListItemComponent implements OnInit {

  @Input('group')
  public group: GroupModel;

  constructor(public router: Router) { }

  ngOnInit() {
  }

  navigateToGroupId() {
    this.router.navigate(['groups',this.group.idField])
  }

}
