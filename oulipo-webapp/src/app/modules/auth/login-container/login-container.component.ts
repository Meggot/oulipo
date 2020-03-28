import { Component, OnInit } from '@angular/core';
import {SessionService} from '../../../services/session.service';
import { Router } from '@angular/router';
import * as fromAppState from '../../../store/app.reducers'
import * as fromAuth from '../store/auth.reducers'
import * as AuthActions from '../store/auth.actions'
import { Store } from '@ngrx/store';
import { Subscription, Observable } from 'rxjs';
import * as AppActions from '../../core/components/app.actions'

@Component({
  selector: 'app-login-container',
  templateUrl: './login-container.component.html',
  styleUrls: ['./login-container.component.css']
})
export class LoginContainerComponent implements OnInit {

  constructor(public sessionService: SessionService,
    public store: Store<fromAppState.AppState>,
    public router: Router) { }
    authState: Observable<fromAuth.State>;

  ngOnInit() {
    this.authState = this.store.select('auth');
  }

  logout() {
    this.store.dispatch(new AuthActions.Logout());
  }
}
