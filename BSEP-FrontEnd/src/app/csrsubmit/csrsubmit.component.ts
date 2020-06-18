import { Component, OnInit } from '@angular/core';
import { CsrService } from '../services/csr.service';

@Component({
  selector: 'app-csrsubmit',
  templateUrl: './csrsubmit.component.html',
  styleUrls: ['./csrsubmit.component.css']
})
export class CSRSubmitComponent implements OnInit {
  data: String;

  constructor(private csrService: CsrService) { }

  ngOnInit(): void {
  }

  onClick(){
    this.csrService.csrSubmit(this.data);
  }

}
