import {Component, Input, OnChanges, OnDestroy, OnInit} from '@angular/core';
import {ProjectModel} from '../../models/project.model';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-project-dropdown',
  templateUrl: './project-dropdown.component.html',
  styleUrls: ['./project-dropdown.component.css']
})
export class ProjectDropdownComponent implements OnInit {

  @Input('projects')
  projects: ProjectModel[];

  projectResponse: Subscription;

  @Input('isOpen')
  isOpen: boolean = false;

  constructor() {
  }

  ngOnInit() {
  }

}
