import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';

import * as AccountActions from '../../../../accounts/store/account.actions'
import { Observable } from 'rxjs';

@Component({
  selector: 'app-inbox-bar',
  templateUrl: './inbox-bar.component.html',
  styleUrls: ['./inbox-bar.component.css']
})
export class InboxBarComponent implements OnInit {

  viewingPanel = 1;


  constructor() { 
  }

  ngOnInit() {
  }
  
  setViewingPanel(panel: number) {
    this.viewingPanel = panel;
  }

}
