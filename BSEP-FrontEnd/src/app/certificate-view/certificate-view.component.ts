import { Component, OnInit } from '@angular/core';
import { SubjectData } from '../model/subjectData';
import { ExtensionDTO } from '../modelDTO/extensionDTO';
import { CsrDTO } from '../modelDTO/csrDTO';
import { ActivatedRoute } from '@angular/router';
import { CertificateService } from '../services/certificate.service';

@Component({
  selector: 'app-certificate-view',
  templateUrl: './certificate-view.component.html',
  styleUrls: ['./certificate-view.component.css']
})
export class CertificateViewComponent implements OnInit {

  public subjectData: SubjectData;
  public extension: ExtensionDTO; 
  public csr: CsrDTO;
  public certificate: String;
  fullNameValidation: boolean;

  constructor(
    private route: ActivatedRoute,
    private certificateService: CertificateService,
  ) {  
    this.subjectData = new SubjectData();
    this.csr = new CsrDTO();
  }

  ngOnInit(): void {
    this.extension = new ExtensionDTO();
    this.getCertificate();

  }

 
  getCertificate(){
    const id = +this.route.snapshot.paramMap.get('id');
    this.certificateService.getCertificate(id).subscribe(data => {this.certificate = data; });
  }
  

}

