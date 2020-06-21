import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SubjectData } from '../model/subjectData';
import { areAllEquivalent } from '@angular/compiler/src/output/output_ast';
import { CertificateService } from '../services/certificate.service';
import { CsrService } from '../services/csr.service';
import { CsrDTO } from '../modelDTO/csrDTO';
import { ExtensionDTO } from '../modelDTO/extensionDTO';

@Component({
  selector: 'app-new-certificate',
  templateUrl: './new-certificate.component.html',
  styleUrls: ['./new-certificate.component.css']
})
export class NewCertificateComponent implements OnInit {
  public subjectData: SubjectData;
  public extension: ExtensionDTO; 
  public csr: CsrDTO;
  fullNameValidation: boolean;

  constructor(
    private route: ActivatedRoute,
    private certificateService: CertificateService,
    private csrService: CsrService,
  ) {  
    this.subjectData = new SubjectData();
    this.csr = new CsrDTO();
  }

  addExtension(){
    debugger;
    this.subjectData.extensions.push(this.extension);
    alert(this.subjectData.extensions[0].isCritical)
    this.extension = {oid: "", isCritical: undefined, value: undefined};
  }

  ngOnInit(): void {

    this.extension = new ExtensionDTO;
    this.getCSRdata();
  }

  getCSRdata(){
    const id = +this.route.snapshot.paramMap.get('id');
    this.csrService.getCsr(id).subscribe(csr => this.csr = csr);
    if(this.csr.commonName){
      alert(this.csr.commonName);
    }
  }
  

  validateSubjectData() {
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
      this.createCertificate(this.subjectData);
      //Call createCertificate method!
    }

  }



  createCertificate(SubjectData) {
    //To-Do pozvati service
    // alert("Ovo je subject data mail: " + this.subjectData.email)
    this.certificateService.addSubjectData(this.subjectData)
  }

}
