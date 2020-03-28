import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {SessionService} from './session.service';
import {ActivatedRoute, Router} from '@angular/router';

@Injectable()
export class ProjectService {
  hostname;
  pageSize = '12';

  constructor(private http: HttpClient, private sessionService: SessionService, private route: Router, private activatedRoute: ActivatedRoute) {
      this.hostname = sessionService.hostname;
  }

  searchForProjectsByPartialTitle(title: string) {
      const params = new HttpParams().append('title', title);
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: this.sessionService.authToken
      });
      return this.http.get(this.hostname + '/projects/projects', {
        headers, params
      });
  }
}
