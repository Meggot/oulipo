import { Component, OnInit } from '@angular/core';
import { AppState } from './store/app.reducers';
import { Store } from '@ngrx/store';
import * as AppActions from './modules/core/components/app.actions'
import * as fromAuth from './modules/auth/store/auth.reducers'
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'oulipo-pad';
  authenticated: Observable<boolean>

  constructor(private store: Store<AppState>) {
    this.authenticated = store.select('auth', 'authenticated');
  }
  
  ngOnInit() {
    this.authenticated.subscribe(
      (authenticated: boolean) => {
        if (authenticated) {
          this.store.dispatch(new AppActions.LoadApp());
        }
      }
    )
  }
}
