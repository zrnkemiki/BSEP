import { Component, OnInit } from '@angular/core';
import { LogService } from '../service/log.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-log-viewer',
  templateUrl: './log-viewer.component.html',
  styleUrls: ['./log-viewer.component.css']
})
export class LogViewerComponent implements OnInit {

  public logs: String[];
  private currentUserEmail: string;
  private currentUserUsername: string;
  private currentUserType: string;

  private applicationAdministrator: string;
  private applicationEmployee: string;
  private registeredUser: string;

  constructor(
    private router: Router,
    private logService: LogService,
  ) { }

  ngOnInit(){
    this.logs = [];
    this.getLogAlerts();
  }

  getLogAlerts() {
    this.logService.logObservable.subscribe(data => this.logs = data);
    this.logService.getAlerts();
  }

  

  returnHome() {
    this.router.navigate(["/homepage"]);
  }

}


