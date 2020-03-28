import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';

import * as fromAppState from '../../../../store/app.reducers'
import * as AuthActions from '../../store/auth.actions'
import { Store } from '@ngrx/store';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  registerResponse: Subscription;
  formSubmitted = false;
  response: any;
  registerFailed = false;

  constructor(private store: Store<fromAppState.AppState>) {
  }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    this.registerForm = new FormGroup({
      username: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required, Validators.email]),
      password: new FormControl(null, [Validators.required])
    });
  }

  onSubmit() {
    this.store.dispatch(new AuthActions.TryRegister({
      username: this.registerForm.value.username,
      email: this.registerForm.value.email,
      hashedPassword: this.registerForm.value.password
    }));
  }
}
