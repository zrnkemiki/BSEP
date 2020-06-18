import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { NewCertificateComponent } from './new-certificate/new-certificate.component';
import { HomepageComponent } from './homepage/homepage.component';
import { AuthGuard } from './helpers/auth.guard';
import { LoginCAComponent } from './login-ca/login-ca.component';
import { CSRSubmitComponent } from './csrsubmit/csrsubmit.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'new-certificate', component: NewCertificateComponent },
  //Disabled AuthGuard
  //{ path: 'new-certificate', component: NewCertificateComponent, canActivate: [AuthGuard] },
  { path: 'login-ca', component: LoginCAComponent, canActivate: [AuthGuard]},
  { path: '', component: HomepageComponent },
  { path: 'csr-submit', component: CSRSubmitComponent},

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
