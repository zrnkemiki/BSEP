import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { CsrDTO } from '../modelDTO/csrDTO';
import { CsrService } from '../services/csr.service';

@Component({
  selector: 'app-csr-request-viewer',
  templateUrl: './csr-request-viewer.component.html',
  styleUrls: ['./csr-request-viewer.component.css']
})
export class CsrRequestViewerComponent implements OnInit {

  public csrs: CsrDTO[];
  private currentUserEmail: string;
  private currentUserUsername: string;
  private currentUserType: string;

  private applicationAdministrator: string;
  private applicationEmployee: string;
  private registeredUser: string;

  constructor(
    private router: Router,
    private csrService: CsrService,
    private authentication: AuthenticationService,
  ) { }

  ngOnInit(){
    this.getCSRrequests();
  }

  getCSRrequests() {
    this.csrService.csrAsObservable.subscribe(csrs => this.csrs = csrs);
    this.csrService.getAll();
  }


  approveCSR(id) {
    this.router.navigate(["/new-certificate/" + id]);

  }

  returnHome() {
    this.router.navigate(["/homepage"]);
  }

}

