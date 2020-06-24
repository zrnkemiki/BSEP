import { Injectable } from '@angular/core';
import { SubjectData } from '../model/subjectData';
import { BehaviorSubject, Subject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CsrDTO } from '../modelDTO/csrDTO';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  //EXAMPLE TO-DO
  private url = "http://localhost:9003";
  private subjectDataSource = new BehaviorSubject<SubjectData[]>([]);
  subjectDataObservable = this.subjectDataSource.asObservable();
  private subjectDatas = [];

  constructor(private http: HttpClient) { }

  //EXAMPLE TO-DO.. Proveriti sta vraca backend  + bezbednost sto se tice slanja objekta! 
  //Napraviti DTO na bekendu kako bi se vadili podaci
  addSubjectData(subjectData) {
    this.http.post<SubjectData>(this.url + "/subject-data", subjectData)
      .subscribe(
        addedSubjectData => {
          this.subjectDatas.push(addedSubjectData);
          this.subjectDataSource.next(this.subjectDatas);
          alert("Successfully added new subject data. New " + subjectData.email + "  added.");
        }
      )
  }

  getCertificates(){
    this.http.get<SubjectData[]>('http://localhost:9003/country-ca/getAllCertificates')
    .subscribe(data => {
      this.subjectDatas = data;
      this.subjectDataSource.next(this.subjectDatas);
    });
  }

  getCertificate(id: number) : Observable<any>{
      return this.http.get<String>('http://localhost:9003/country-ca/getCertificate' + "/" + id);
  }

  revokeCertificate(id: number) : Observable<any>{
      return this.http.get<String>('http://localhost:9003/country-ca/revoke' + "/" + id);
  }


  
}
