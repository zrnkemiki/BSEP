import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CsrDTO } from '../modelDTO/csrDTO';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { SubjectData } from '../model/subjectData';

@Injectable({
  providedIn: 'root'
})
export class CsrService {
  
  

  private csrSource = new BehaviorSubject<CsrDTO[]>([]);
  csrAsObservable = this.csrSource.asObservable();
  private csrs = [];

  constructor(private http : HttpClient, private router: Router) { }

  csrSubmit(csr: String){
    this.http.post<CsrDTO>('http://localhost:9003/country-ca/csrData', csr)
    .subscribe(
      csr => {
        this.csrs.push(csr);
        this.csrSource.next(this.csrs);
        alert("Successfuly sent csr");
      }
    )
  }

  getAll() {
    this.http.get<CsrDTO[]>('http://localhost:9003/country-ca/getAll')
      .subscribe(csrs => {
        this.csrs = csrs;
        this.csrSource.next(this.csrs);
      });
  }

  getCsr(id: number) : Observable<any>{
    return this.http.get<CsrDTO>('http://localhost:9003/country-ca/getCSR' + "/" + id);
  }

  generateCertificate(id: number) : Observable<any>{
    return this.http.get<CsrDTO>('http://localhost:9003/country-ca/generateCertificate/1');
  }

}
