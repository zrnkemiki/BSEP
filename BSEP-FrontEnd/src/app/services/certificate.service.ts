import { Injectable } from '@angular/core';
import { SubjectData } from '../model/subjectData';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {
  //EXAMPLE TO-DO
  private url = "http://localhost:8080/..";
  private subjectDataSource = new BehaviorSubject<SubjectData[]>([]);
  subjectDataObservable = this.subjectDataSource.asObservable();
  private subjectDatas = [];

  constructor(private http: HttpClient) { }

  //EXAMPLE TO-DO.. Proveriti sta vraca backend  + bezbednost sto se tice slanja objekta! 
  //Napraviti DTO na bekendu kako bi se vadili podaci
  addSubjectData(subjectData) {
    this.http.post<SubjectData>(this.url, subjectData)
      .subscribe(
        addedSubjectData => {
          this.subjectDatas.push(addedSubjectData);
          this.subjectDataSource.next(this.subjectDatas);
          alert("Successfully added new subject data. New " + subjectData.surname + "  added.");
        }
      )
  }
}
