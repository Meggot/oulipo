import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import { MessageModel } from '../../shared/models/message.model';
import { Observable, forkJoin, of, from, pipe } from 'rxjs';
import { map, concat, combineLatest, mergeMap, flatMap, switchMap, mapTo, merge, subscribeOn, tap } from 'rxjs/operators';
import { stringify } from '@angular/compiler/src/util';
import { Message } from '@angular/compiler/src/i18n/i18n_ast';
import * as fromInbox from '../../inbox/store/inbox.reducers';
import { AccountModel } from '../../shared/models/account.model';
import * as InboxActions from '../store/inbox.actions';
import { ActivatedRoute, Params, ParamMap } from '@angular/router';
@Component({
  selector: 'app-inbox-container',
  templateUrl: './inbox-container.component.html',
  styleUrls: ['./inbox-container.component.css']
})
export class InboxContainerComponent implements OnInit {

  receivedMessages: Observable<MessageModel[]>;
  sentMessages: Observable<MessageModel[]>;
  activeThread: { username: string, thread: { message: MessageModel, received: boolean }[] } = null;
  uniqueThreads: Observable<{ username: string, thread: { message: MessageModel, received: boolean }[] }[]>;
  thisAccount: Observable<AppState>;

  @ViewChild('searchText')
  searchText: ElementRef;

  constructor(private store: Store<AppState>,
    private activatedRoute: ActivatedRoute) {
    this.receivedMessages = this.store.select('inbox', 'receivedMessages').pipe(
      tap((receivedMessages: MessageModel[]) => {
        this.init();
      })
    )
    this.sentMessages = this.store.select('inbox', 'sentMessages').pipe(
      tap((sentMessages: MessageModel[]) => {
        this.init();
      })
    )
  }

  ngOnInit() {
    this.init()
    try {
      this.activatedRoute.firstChild.paramMap.subscribe(
        (paramMap: ParamMap) => {
          if (paramMap.has('username')) {
            this.uniqueThreads.subscribe(
              pipe((threads: { username: string, thread: { message: MessageModel, received: boolean }[] }[]) => {
                  const username = paramMap.get('username');
                  let toBeActive = threads.find(thread => thread.username === username)
                  if (toBeActive !== undefined) {
                    this.activeThread = toBeActive;
                  } else {
                    this.attemptThreadCreation(paramMap.get('username'));
                  }
                }
              ))
          } else {
            console.log(paramMap)
          }
        }
      )
    } catch (error) { }
  }

  init() {
    this.uniqueThreads = of(null);
    this.uniqueThreads = this.store.select('inbox').pipe(
      map((state: fromInbox.State) => {
        return [
          state.receivedMessages,
          state.sentMessages
        ]
      }),
    ).pipe(
      map(([receivedMessages, sentMessages]) => {
        let mergedThreads: { username: string, thread: { message: MessageModel, received: boolean }[] }[] = [];
        sentMessages.forEach((message) => {
          let activeThreadTo = mergedThreads.find(any => any.username === message.toUsername)
          if (activeThreadTo === undefined) {
            mergedThreads.push({ username: message.toUsername, thread: [{ message: message, received: false }] })
          } else {
            activeThreadTo.thread = [...activeThreadTo.thread, { message: message, received: false }];
          }
        })
        receivedMessages.forEach((message) => {
          let activeThreadFrom = mergedThreads.find(any => any.username === message.fromUsername)
          if (activeThreadFrom === undefined) {
            mergedThreads.push({ username: message.fromUsername, thread: [{ message: message, received: true }] })
          } else {
            activeThreadFrom.thread = [...activeThreadFrom.thread, { message: message, received: true }];
          };
        })
        mergedThreads.forEach(thread => {
          thread.thread.sort((x, y) => x.message.sentAt > y.message.sentAt ? 1 : -1)
        });
        return mergedThreads;
      })
    );
  }


  foundAccount(account: AccountModel) {
    console.log(account)
    this.searchText.nativeElement.value = account.username;
  }

  setActiveThread(thread) {
    this.activeThread = thread;
  }

  attemptThreadCreation(username: string) {
    this.activeThread = { username: username, thread: [] };
  }
}

export class MessageThread {
  constructor(public messageThread: { username: string, thread: { message: MessageModel, received: boolean }[] }) { }
}
