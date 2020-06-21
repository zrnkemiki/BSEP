import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { CsrDTO } from '../modelDTO/csrDTO';

@Component({
  selector: 'app-csr-request-viewer',
  templateUrl: './csr-request-viewer.component.html',
  styleUrls: ['./csr-request-viewer.component.css']
})
export class CsrRequestViewerComponent implements OnInit {

  public csrRequestes: CsrDTO[];
  test1: CsrDTO;
  private currentUserEmail: string;
  private currentUserUsername: string;
  private currentUserType: string;

  private applicationAdministrator: string;
  private applicationEmployee: string;
  private registeredUser: string;

  constructor(
    private router: Router,
    private authentication: AuthenticationService,
  ) { }

  ngOnInit(){
    this.test1 = new CsrDTO();
    this.test1.commonName = "Kopanja";
    this.test1.email = "kopanja@kopanja.com";
    this.test1.id = 1;

    this.csrRequestes = [this.test1];

  }


  approveCSR(id) {
    alert("TO_DO!")

  }

  returnHome() {
    this.router.navigate(["/homepage"]);
  }

}

