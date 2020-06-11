import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SubjectData } from '../model/subjectData';
import { areAllEquivalent } from '@angular/compiler/src/output/output_ast';
import { CertificateService } from '../services/certificate.service';

@Component({
  selector: 'app-new-certificate',
  templateUrl: './new-certificate.component.html',
  styleUrls: ['./new-certificate.component.css']
})
export class NewCertificateComponent implements OnInit {
  public subjectData: SubjectData;
  fullNameValidation: boolean;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private certificateService: CertificateService,
  ) {  
    
  }

  ngOnInit(): void {
    this.subjectData = new SubjectData();
  }
  

  validateSubjectData() {
    debugger;
    if (this.subjectData.commonName == "" || this.subjectData.commonName.length <= 2) {
      alert("Fullname must be more than 2 characters long.")
    }
    else if (this.subjectData.surname == "") {
      alert("You must enter surname.")
    }
    else if (this.subjectData.givenName == "") {
      alert("You must enter givenName.")
    }
    else if (this.subjectData.organization == "") {
      alert("You must enter organization.")
    }
    else if (this.subjectData.organizationUnit == "") {
      alert("You must enter organization unit.")
    }
    else if (this.subjectData.country == "") {
      alert("You must enter country.")
    }
    else if (this.subjectData.email == "") {
      alert("You must enter email.")
    }
    else {
      alert("CommonName: " + this.subjectData.commonName);
      this.createCertificate(this.subjectData);
      //Call createCertificate method!
    }

  }



  createCertificate(SubjectData) {
    //To-Do pozvati service
    alert("Ovo je subject data mail: " + this.subjectData.email)
    this.certificateService.addSubjectData(this.subjectData)
  }

}
