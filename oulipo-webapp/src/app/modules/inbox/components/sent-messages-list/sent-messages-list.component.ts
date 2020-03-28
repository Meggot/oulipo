import { Component, OnInit } from '@angular/core';
import { MessageModel } from 'src/app/modules/shared/models/message.model';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';

@Component({
  selector: 'app-sent-messages-list',
  templateUrl: './sent-messages-list.component.html',
  styleUrls: ['./sent-messages-list.component.css']
})
export class SentMessagesListComponent implements OnInit {

  sentMessages: Observable<MessageModel[]>

  constructor(private store: Store<AppState>) { }

  ngOnInit() {
    this.sentMessages = this.store.select('inbox', 'sentMessages');
  }

}
