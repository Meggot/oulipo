import { NgModule } from '@angular/core';
import { InboxContainerComponent } from 'src/app/modules/inbox/inbox-container/inbox-container.component';
import { ReceivedMessagesListComponent } from 'src/app/modules/inbox/components/received-messages-list/received-messages-list.component';
import { ComposeMessageComponent } from 'src/app/modules/inbox/components/compose-message/compose-message.component';
import { SentMessagesListComponent } from 'src/app/modules/inbox/components/sent-messages-list/sent-messages-list.component';
import { InboxRoutingModule } from './inbox-routing.module';
import { SharedModule } from '../shared/shared.module';
import { InboxApi } from './store/inbox.api';

@NgModule({
    declarations: [
        InboxContainerComponent,
        ReceivedMessagesListComponent,
        ComposeMessageComponent,
        SentMessagesListComponent
    ],
    imports: [
        InboxRoutingModule,
        SharedModule
    ],
    providers: [
        InboxApi
    ]
})
export class InboxModule {}