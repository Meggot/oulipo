import { Component, OnInit } from '@angular/core';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import * as fromAuth from '../../../auth/store/auth.reducers'
import { PostboxModel } from 'src/app/modules/shared/models/postbox.model';
import { map } from 'rxjs/operators';
import * as ProjectActions from '../../../projects/store/project.actions'
import * as InboxActions from '../../../inbox/store/inbox.actions'
import { ProjectModel } from 'src/app/modules/shared/models/project.model';
@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  public authState: Observable<fromAuth.State>
  public postbox: Observable<PostboxModel>;

  constructor(private store: Store<AppState>) { 
    this.authState = this.store.select('auth')
    this.postbox = this.store.select('notifications', 'postbox')

  }

  ngOnInit() {
    this.postbox.subscribe(
      (postbox: PostboxModel) => {
          postbox.unreadMail.forEach((mail) => {
            switch(mail.notification.type) {
              case INBOX_MESSAGE_RECEIVED:
                this.store.dispatch(new InboxActions.RefreshThisInbox())
                return;
              case PROJECT_PART_POSTED:
              case PROJECT_PART_UPDATED:
              case PROJECT_TAG_CREATED:
              case PROJECT_TAG_REMOVED:
              case PROJECT_EDIT_UPDATED:
                let sub = this.store.select('projects', 'viewingProject').subscribe(
                  (viewingProject: ProjectModel) => {
                    if (viewingProject.projectId === mail.notification.entityId) {
                    this.store.dispatch(new ProjectActions.LoadandSetViewingProjectById(mail.notification.entityId))
                    }
                  }
                )
                sub.unsubscribe();
                return;
              default:
                return;
            }
          })
      }
      )
  }

}


export const INBOX_MESSAGE_RECEIVED = "INBOX_MESSAGE_RECEIVED"
export const PROJECT_TAG_CREATED = "PROJECT_TAG_CREATED"
export const PROJECT_TAG_REMOVED = "PROJECT_TAG_REMOVED"
export const PROJECT_PART_POSTED = "PROJECT_PART_POSTED"
export const PROJECT_PART_UPDATED = "PROJECT_PART_UPDATED"
export const PROJECT_EDIT_POSTED = "PROJECT_EDIT_POSTED"
export const PROJECT_EDIT_UPDATED = "PROJECT_EDIT_UPDATED"
export const AUTHOR_PART_UPDATED = "AUTHOR_PART_UPDATED"
export const AUTHOR_ROLE_UPDATED = "AUTHOR_ROLE_UPDATED"
