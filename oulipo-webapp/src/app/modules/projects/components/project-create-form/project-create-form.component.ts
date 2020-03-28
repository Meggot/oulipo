import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ProjectService} from '../../../../services/project.service';
import {Router} from '@angular/router';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import * as ProjectAction from "../../store/project.actions";
import { CreateProjectModel } from 'src/app/modules/shared/models/create-project.model';
import { Observable } from 'rxjs';
import { SubErroredResponse } from 'src/app/modules/shared/models/subErroredResponse';
@Component({
  selector: 'app-project-create-form',
  templateUrl: './project-create-form.component.html',
  styleUrls: ['./project-create-form.component.css']
})
export class ProjectCreateFormComponent implements OnInit {
  createProjectForm: FormGroup;
  addedTags: string[]

  createProjectFailMessage: Observable<SubErroredResponse>;

  constructor(private projectService: ProjectService,
              private route: Router,
              private store: Store<AppState>) {
                this.createProjectFailMessage = this.store.select('projects', 'createProjectFailMessage')
                this.addedTags = [];
  }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    this.createProjectForm = new FormGroup({
      title: new FormControl(null, Validators.required),
      synopsis: new FormControl(null, Validators.required),
      tags: new FormControl(this.addedTags),
      visibilityType: new FormControl(null, Validators.required),
      projectType: new FormControl(null, Validators.required),
      sourcingType: new FormControl(null, Validators.required)
    });
  }

  onSubmitForm() {
    let createProject = new CreateProjectModel(
      this.createProjectForm.value.title,
      this.createProjectForm.value.synopsis,
      this.createProjectForm.value.visibilityType,
      this.createProjectForm.value.projectType,
      this.createProjectForm.value.sourcingType
      );
    this.store.dispatch(new ProjectAction.CreateProject(createProject))
  }


  addNewTag(tagValue: string) {
    this.addedTags.push(tagValue);
  }

  removeTag(index: number){
    this.addedTags.splice(index, 1);
  }
  
}
