import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, UrlSegment} from '@angular/router';

@Component({
  selector: 'app-error-container',
  templateUrl: './error-container.component.html',
  styleUrls: ['./error-container.component.css']
})
export class ErrorContainerComponent implements OnInit {

  message: string;

  constructor(private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.url.subscribe(
      (segment: UrlSegment[]) => {
        this.message = segment[0].path;
        this.handleErrorMessage(this.message);
      }
    );
  }


  private handleErrorMessage(message: string) {
    switch (message) {
      case 'not-logged-in':
        this.message = 'You must be logged in to access that resource.';
        break;
    }
  }

  navigateToLogin() {
    this.router.navigate(['auth']);
  }
}
