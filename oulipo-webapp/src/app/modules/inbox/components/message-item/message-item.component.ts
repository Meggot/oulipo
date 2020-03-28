import { Component, OnInit, Input } from '@angular/core';
import { MessageModel } from 'src/app/modules/shared/models/message.model';

@Component({
  selector: 'app-message-item',
  templateUrl: './message-item.component.html',
  styleUrls: ['./message-item.component.css']
})
export class MessageItemComponent implements OnInit {

  @Input()
  public message: MessageModel;

  constructor() { }

  ngOnInit() {
  }

}
