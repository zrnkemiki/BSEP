import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CsrService {

  constructor(private http : HttpClient, private router: Router) { }

  csrSubmit(csr: String){
    this.http.post<any>('http://localhost:9003/country-ca/csrData', csr)
    .subscribe(
      data => {
        alert("Successfuly sent");
      }
    )
}
}
