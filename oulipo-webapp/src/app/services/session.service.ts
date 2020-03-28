import {Injectable} from '@angular/core';

@Injectable()
export class SessionService {
  public isLoggedIn = false;
  // public hostname = 'http://34.240.194.250:13000/api';
  // public hostname = 'http://52.208.213.253:13000/api';
  public hostname = 'http://localhost:13000/api'
  public authToken: string;
  public username: string;
  public role: number = 0;
  constructor() {
    if (localStorage.getItem("authToken") !== null) {
      this.authToken = localStorage.getItem("authToken")
      this.username = localStorage.getItem("username")
      this.role = localStorage.getItem("role") ? +localStorage.getItem("role") : 0;
      this.isLoggedIn = true;
    }
  }
}
