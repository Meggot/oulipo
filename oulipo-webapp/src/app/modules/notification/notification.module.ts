import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationDisplayComponent } from './components/notification-display/notification-display.component';
import { NotificationItemComponent } from './components/notification-item/notification-item.component';
import { NotificationApi } from './store/notification.api';

@NgModule({
  declarations: [
    NotificationDisplayComponent,
    NotificationItemComponent
  ],
  imports: [
    CommonModule
  ],
  providers: [
    NotificationApi
  ]
})
export class NotificationModule { }
