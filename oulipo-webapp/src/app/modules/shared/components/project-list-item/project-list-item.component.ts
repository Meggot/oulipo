import {Component, Input, OnInit} from '@angular/core';
import {ProjectModel} from '../../models/project.model';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-project-list-item',
  templateUrl: './project-list-item.component.html',
  styleUrls: ['./project-list-item.component.css']
})
export class ProjectListItemComponent implements OnInit {
  @Input('project')
  project: ProjectModel;

  @Input('detailLevel')
  detailLevel = 1;

  constructor(private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
  }

  navigateToProjectDetail() {
    this.router.navigate(['/projects', this.project.projectId]);
  }

}
