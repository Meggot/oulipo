import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-home-container',
  templateUrl: './home-container.component.html',
  styleUrls: ['./home-container.component.css']
})
export class HomeContainerComponent implements OnInit {

  textEntered: any;

  curtail = 'a';

  curtailAdhered = true;

  isLoggedIn = false;

  constructor(private sessionService: SessionService) { }

  ngOnInit() {
    if (this.sessionService.isLoggedIn) {
      this.isLoggedIn = true;
    }
  }

  validateCurtail(content: string) {
    console.log('VALIDATING: ' + content)
    if (content.indexOf(this.curtail) > -1) {
      this.curtailAdhered = false;
    } else {
      this.curtailAdhered = true;
    }
  }

}
