import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LogViewerComponent } from './log-viewer/log-viewer.component';


const routes: Routes = [
{ path: 'log-view', component: LogViewerComponent },
]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
