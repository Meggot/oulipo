import {Component, Input, OnInit} from '@angular/core';
import {ProjectModel} from '../../../../shared/models/project.model';
import {ActivatedRoute} from '@angular/router';
import {MatrixModel} from '../../../../shared/models/matrix.model';
import {MatrixService} from '../../../../../services/matrix.service';
import {Subscription} from 'rxjs';
import {HateosResponseModel} from '../../../../shared/models/hateos-response.model';

@Component({
  selector: 'app-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  styleUrls: ['./project-dashboard.component.css']
})
export class ProjectDashboardComponent implements OnInit {

  @Input()
  project: ProjectModel;
  matrix: MatrixModel;
  matrixResponse: Subscription;
  rolesSelected = false;
  tagsSelected = false;

  constructor(private router: ActivatedRoute,
              private matrixService: MatrixService) {
  }

  ngOnInit() {
    
  }

  clickRoles() {
    this.rolesSelected = !this.rolesSelected;
  }

  clickTags() {
    this.tagsSelected = !this.tagsSelected;
  }

}
