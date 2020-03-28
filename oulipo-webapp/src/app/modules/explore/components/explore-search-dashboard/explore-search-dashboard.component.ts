import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { SessionService } from 'src/app/services/session.service';
import { Router } from '@angular/router';
import { ProjectModel } from 'src/app/modules/shared/models/project.model';
import { HateosResponseModel } from 'src/app/modules/shared/models/hateos-response.model';

@Component({
  selector: 'app-explore-search-dashboard',
  templateUrl: './explore-search-dashboard.component.html',
  styleUrls: ['./explore-search-dashboard.component.css']
})
export class ExploreSearchDashboardComponent implements OnInit {

  exploreSearchForm: FormGroup;
  pageSize = '20';

  @Output('search')
  private searchParam: EventEmitter<{ title: string, tags: string, type: string, author: string, sort: string }> =
    new EventEmitter<{ title: string, tags: string, type: string, author: string, sort: string }>();


  constructor(private http: HttpClient,
    private sessionService: SessionService,
    private route: Router) { }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    this.exploreSearchForm = new FormGroup({
      title: new FormControl(''),
      tags: new FormControl(''),
      type: new FormControl(''),
      author: new FormControl(''),
      sort: new FormControl(''),
      editable: new FormControl(''),
      oulipo: new FormControl(''),
      private: new FormControl('')
    })
  }


  onSearchButton() {
    this.searchParam.emit(
      {
        title: this.exploreSearchForm.value.title,
        tags: this.exploreSearchForm.value.tags,
        type: this.exploreSearchForm.value.type,
        author: this.exploreSearchForm.value.author,
        sort: this.exploreSearchForm.value.sort
      }
    );
  }

}
