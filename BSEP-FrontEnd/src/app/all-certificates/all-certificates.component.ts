import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';
import { CsrService } from '../services/csr.service';
import { Router } from '@angular/router';
import { SubjectData } from '../model/subjectData';
import { CertificateService } from '../services/certificate.service';

@Component({
  selector: 'app-all-certificates',
  templateUrl: './all-certificates.component.html',
  styleUrls: ['./all-certificates.component.css']
})
export class AllCertificatesComponent implements OnInit {

  public certificates: SubjectData[];
  public aa : SubjectData;
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
    private certificateService: CertificateService,
  ) { }

  ngOnInit(){
    this.certificates = [];
    this.aa = new SubjectData();
    this.aa.id = "1";
    this.aa.commonName = "zrnkemiki";
    this.certificates.push(this.aa);
    //this.getCertificates();
  }

  getCertificates() {
    this.certificateService.subjectDataObservable.subscribe(data => this.certificates = data);
    this.certificateService.getCertificates();
  }

  viewCertificate(id){
    this.router.navigate(["/ceritificate-view/" + id]);
  }

  revokeCertificate(id){
    this.certificateService.subjectDataObservable.subscribe(data => this.certificates = data);
    this.certificateService.revokeCertificate(id);
  }

  returnHome() {
    this.router.navigate(["/homepage"]);
  }

}

