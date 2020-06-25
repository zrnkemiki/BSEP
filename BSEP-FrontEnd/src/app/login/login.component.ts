import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginDTO } from '../modelDTO/loginDTO';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginDTO: LoginDTO;
  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit() {
    const currentUser = this.authenticationService.currentUserValue;
    if(currentUser){
      this.router.navigate(['']);
    }
    else{
      this.loginDTO = new LoginDTO();
    }
  }

  register() {
    this.router.navigate(['/registration'])
  }

  onClick() {
    if (this.loginDTO.username && this.loginDTO.password){
      this.authenticationService.login(this.loginDTO);
      this.authenticationService.currentUser.subscribe(
  
        (result) => {
          if (result) {
            this.router.navigate(['/homepage'])
          }
          else {
            //this.toastr.error('error logging');
          }
        });
        this.router.navigate([''])
    }

   

  }

}

