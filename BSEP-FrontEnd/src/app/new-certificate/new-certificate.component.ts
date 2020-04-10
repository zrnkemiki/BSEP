import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SubjectData } from '../model/subjectData';

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
  ) {  //KAKO RESITI OVAJ PROBLEM??
    this.subjectData = {commonName : "", surname :"", givenName:"", organization:"", organizationUnit:"", 
    country:"", email:"", dateFrom:"", dateUntil:"", uid: undefined }
  }

  ngOnInit(): void {
  }

  validateSubjectData(){
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
      //Call createCertificate method!
    }
  
  }

 

  createCertificate(){
    //To-Do pozvati service
  }

}