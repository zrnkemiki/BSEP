import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LogService {
  //EXAMPLE TO-DO
  private logSource = new BehaviorSubject<String[]>([]);
  logObservable = this.logSource.asObservable();
  private logs = [];

  constructor(private http: HttpClient) { }


  getAlerts(){
    this.http.get<String[]>('https://localhost:9005/testCEP/1')
    .subscribe(data => {
      this.logs = data;
      this.logSource.next(this.logs);
    });
  }
}
