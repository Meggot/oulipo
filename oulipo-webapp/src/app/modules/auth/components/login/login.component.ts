import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { SessionService } from '../../../../services/session.service';
import { Router } from '@angular/router';

import * as fromAppState from '../../../../store/app.reducers'
import * as fromAuthState from '../../store/auth.reducers'
import * as AuthActions from '../../store/auth.actions'
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(
    private store: Store<fromAppState.AppState>) {
  }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  onLogin() {
    this.store.dispatch(new AuthActions.TryLogin({
      username: this.loginForm.value.username,
      hashedPassword: this.loginForm.value.password
    }));
    this.loginForm.value.username = '';
    this.loginForm.value.password = '';
  }

}
