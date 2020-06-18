import { Component, OnInit } from '@angular/core';
import { LoginCaDTO } from '../modelDTO/loginCaDTO';
import { AuthenticationService } from '../services/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-ca',
  templateUrl: './login-ca.component.html',
  styleUrls: ['./login-ca.component.css']
})
export class LoginCAComponent implements OnInit {

  public loginCaDTO: LoginCaDTO;
  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit() {
    this.loginCaDTO = new LoginCaDTO();
  }

  register() {
    this.router.navigate(['/registration'])
  }

  onClick() {
    if (this.loginCaDTO.alias && this.loginCaDTO.privateKeyPassword){
      this.authenticationService.loginCA(this.loginCaDTO);
      this.authenticationService.currentUser.subscribe(
  
        (result) => {
          if (result) {
            this.router.navigate(['/homepage'])
          }
          else {
            //this.toastr.error('error logging');
          }
        });
  
    }

   

  }

}

