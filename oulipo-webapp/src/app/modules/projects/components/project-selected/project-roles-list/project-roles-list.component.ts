import {Component, Input, OnInit} from '@angular/core';
import {ProjectRolesModel} from '../../../../shared/models/project-roles.model';

@Component({
  selector: 'app-project-roles-list',
  templateUrl: './project-roles-list.component.html',
  styleUrls: ['./project-roles-list.component.css']
})
export class ProjectRolesListComponent implements OnInit {

  @Input('roles')
  roles: ProjectRolesModel[];

  rolesSelected: boolean = false;

  constructor() { }

  ngOnInit() {
  }

  clickRoles() {
    this.rolesSelected = !this.rolesSelected;

  }
}
