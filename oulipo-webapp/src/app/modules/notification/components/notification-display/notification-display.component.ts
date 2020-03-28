import { Component, OnInit } from '@angular/core';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { PostboxModel } from 'src/app/modules/shared/models/postbox.model';

@Component({
  selector: 'app-notification-display',
  templateUrl: './notification-display.component.html',
  styleUrls: ['./notification-display.component.css']
})
export class NotificationDisplayComponent implements OnInit {

  postbox: Observable<PostboxModel>;

  constructor(private store: Store<AppState>) { 
    this.postbox = this.store.select('notifications', 'postbox')
  }

  ngOnInit() {
  }

}
