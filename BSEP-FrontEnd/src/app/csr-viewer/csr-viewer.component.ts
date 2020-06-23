import { Component, OnInit } from '@angular/core';
import { ExtensionDTO } from '../modelDTO/extensionDTO';
import { SubjectData } from '../model/subjectData';
import { CsrDTO } from '../modelDTO/csrDTO';
import { ActivatedRoute } from '@angular/router';
import { CertificateService } from '../services/certificate.service';
import { CsrService } from '../services/csr.service';

@Component({
  selector: 'app-csr-viewer',
  templateUrl: './csr-viewer.component.html',
  styleUrls: ['./csr-viewer.component.css']
})
export class CsrViewerComponent implements OnInit {
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

  ngOnInit(): void {
    this.extension = new ExtensionDTO();
    this.getCSRdata();

  }

  generateCertificate(id){
    debugger;
    this.csrService.generateCertificate(id).subscribe(
      csr => {alert("Successfuly created certificate!")},
      error=> {alert("Something went wrong!")}
      );
  }



  getCSRdata(){
    const id = +this.route.snapshot.paramMap.get('id');
    this.csrService.getCsr(id).subscribe(csr => {this.csr = csr; });
  }
  

}
