import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SessionService} from './session.service';
import {Router} from '@angular/router';

@Injectable()
export class MatrixService {
  hostname;
  constructor(private http: HttpClient,
              private sessionService: SessionService,
              private route: Router) {
                this.hostname = sessionService.hostname;
  }

  getMatrixForEntityIdAndType(entityId: number, entityType: string) {
      const header = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: this.sessionService.authToken
      });
      return this.http.get(this.hostname + '/metadata/matrix/' + entityType + '/' + entityId,
        {headers: header });
  }

}
