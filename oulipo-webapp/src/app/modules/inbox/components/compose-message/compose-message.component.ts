import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AppState } from 'src/app/store/app.reducers';
import { Store } from '@ngrx/store';
import { TrySendMessage } from '../../store/inbox.actions';
import { ActivatedRoute, ParamMap, Params } from '@angular/router';

@Component({
  selector: 'app-compose-message',
  templateUrl: './compose-message.component.html',
  styleUrls: ['./compose-message.component.css']
})
export class ComposeMessageComponent implements OnInit {
  
  composeForm: FormGroup;

  @Input()
  to: string;

  constructor(private store: Store<AppState>,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.initForm();
    console.log(this.activatedRoute)
  }

  initForm() {
    this.composeForm = new FormGroup({
      body: new FormControl(null, Validators.required)
    });
  }

  sendMessage() {
    this.store.dispatch(new TrySendMessage({to: this.to, content: this.composeForm.value.body}));
  }

}
