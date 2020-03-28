import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import { ProjectModel } from '../../models/project.model';
import { ProjectService } from 'src/app/services/project.service';
import { HateosResponseModel } from '../../models/hateos-response.model';

@Component({
  selector: 'app-reactive-project-search',
  templateUrl: './reactive-project-search.component.html',
  styleUrls: ['./reactive-project-search.component.css']
})
export class ReactiveProjectSearchComponent implements OnInit, OnChanges {

  @Input()
  searchForm: string;

  projectsFound: ProjectModel[];

  constructor(private projectService: ProjectService) {
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    const searchTerm = this.searchForm;
    if (searchTerm.length >= 5) {
      this.projectService.searchForProjectsByPartialTitle(searchTerm)
        .subscribe(
          (response: HateosResponseModel<{ content: ProjectModel[] }>) => {
            if (response._embedded.content.length > 0) {
              this.projectsFound = response._embedded.content;
            } else {
              this.clear();
            }
          }
        );
    } else {
      this.clear();
    }
  }

  clear() {
    this.projectsFound = [];
  }
}
