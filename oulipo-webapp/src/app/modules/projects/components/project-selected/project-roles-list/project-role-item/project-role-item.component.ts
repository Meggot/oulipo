import {Component, Input, OnInit} from '@angular/core';
import {ProjectRolesModel} from '../../../../../shared/models/project-roles.model';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-project-role-item',
  templateUrl: './project-role-item.component.html',
  styleUrls: ['./project-role-item.component.css']
})
export class ProjectRoleItemComponent implements OnInit {

  @Input()
  roles: ProjectRolesModel[];

  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateToAccountPage(authorId: number) {
    this.router.navigate(['/accounts', authorId]);
  }
}
