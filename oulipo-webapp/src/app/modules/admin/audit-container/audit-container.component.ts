import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';

import { PaginationLinks } from '../../shared/models/pagination-links.model';
import { Observable } from 'rxjs';
import { PaginationData } from '../../shared/models/pagination-data.model';
import { AuditModel } from '../../shared/models/audit.model';

import * as AdminAction from '../store/admin.actions';
import { AppState } from 'src/app/store/app.reducers';

@Component({
  selector: 'app-audit-container',
  templateUrl: './audit-container.component.html',
  styleUrls: ['./audit-container.component.css']
})
export class AuditContainerComponent implements OnInit {

  auditPage: Observable<AuditModel[]>
  auditPageData: Observable<PaginationData>
  auditPageLinks: Observable<PaginationLinks>

  constructor(private store: Store<AppState>) {
    this.auditPage = store.select('admin', 'auditPage');
    this.auditPageData = store.select('admin', 'auditPageData');
    this.auditPageLinks = store.select('admin', 'auditPageLinks');
  }

  ngOnInit() {
    this.store.dispatch(new AdminAction.SearchAudits());
  }

  onFirstPageClick() {
    let sub = this.auditPageLinks.subscribe(
      ((pageLinks: PaginationLinks) => {
        this.store.dispatch(new AdminAction.NavigateAuditPage(pageLinks.first.href));
      })
    )
    sub.unsubscribe
  }

  onPreviousPageClick() {
    let sub = this.auditPageLinks.subscribe(
      ((pageLinks: PaginationLinks) => {
        this.store.dispatch(new AdminAction.NavigateAuditPage(pageLinks.prev.href));
      })
    )
    sub.unsubscribe
  }

  onNextPageClick() {
    let sub = this.auditPageLinks.subscribe(
      ((pageLinks: PaginationLinks) => {
        this.store.dispatch(new AdminAction.NavigateAuditPage(pageLinks.next.href));
      })
    )
    sub.unsubscribe
  }

  onLastPageClick() {
    let sub = this.auditPageLinks.subscribe(
      ((pageLinks: PaginationLinks) => {
        this.store.dispatch(new AdminAction.NavigateAuditPage(pageLinks.last.href));
      })
    )
    sub.unsubscribe
  }
}
