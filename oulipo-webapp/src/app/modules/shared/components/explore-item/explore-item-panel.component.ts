import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ProjectModel } from 'src/app/modules/shared/models/project.model';

@Component({
  selector: 'app-explore-item-panel',
  templateUrl: './explore-item-panel.component.html',
  styleUrls: ['./explore-item-panel.component.css']
})
export class ExploreItemComponentPanel implements OnInit {

  @Input('project')
  public project: ProjectModel;

  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateToProjectDetail() {
    this.router.navigate(['/projects', this.project.projectId]);
  }

}
