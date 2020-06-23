import { Component } from '@angular/core';
import { User } from './model/user';
import { Router } from '@angular/router';
import { AuthenticationService } from './services/authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'BSEP-FrontEnd';  
  //To-Do Current user logged in!
  public currentUser : User;
  public currentAdminCA : boolean;
  public currentAdminMU : boolean;

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
) {}

  
  ngOnInit() {
    this.getCurrentUser();
  }

  getCurrentUser(){
    const currentUser = this.authenticationService.currentUserValue;
    //var user = JSON.parse(localStorage.getItem('currentUser'));
    this.currentUser = new User();
    this.currentUser = currentUser;
  }

  login(){
    window.location.replace('/login')
  }

  register(){
    window.location.replace('/registration')
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    window.location.replace('')
  }
}
