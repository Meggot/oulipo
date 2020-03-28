import { Component, OnInit } from '@angular/core';
import { MessageModel } from 'src/app/modules/shared/models/message.model';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-received-messages-list',
  templateUrl: './received-messages-list.component.html',
  styleUrls: ['./received-messages-list.component.css']
})
export class ReceivedMessagesListComponent implements OnInit {

  receivedMessages: Observable<MessageModel[]>

  constructor(private store: Store<AppState>) { }

  ngOnInit() {
    this.receivedMessages = this.store.select('inbox', 'receivedMessages');
  }
}
