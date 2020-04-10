import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { NewCertificateComponent } from './new-certificate/new-certificate.component';
import { HomepageComponent } from './homepage/homepage.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'new-certificate', component: NewCertificateComponent },
  { path: '', component: HomepageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
