import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { NewCertificateComponent } from './new-certificate/new-certificate.component';
import { HomepageComponent } from './homepage/homepage.component';
import { AuthGuard } from './helpers/auth.guard';
import { LoginCAComponent } from './login-ca/login-ca.component';
import { CSRSubmitComponent } from './csrsubmit/csrsubmit.component';
import { CsrRequestViewerComponent } from './csr-request-viewer/csr-request-viewer.component';
import { CsrViewerComponent } from './csr-viewer/csr-viewer.component';
import { AuthenticationService } from './services/authentication.service';
import { AuthCAGuard } from './helpers/auth.ca.guard';
import { AuthMUGuard } from './helpers/auth.mu.guard';
import { AllCertificatesComponent } from './all-certificates/all-certificates.component';
import { CertificateViewComponent } from './certificate-view/certificate-view.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'new-certificate', component: NewCertificateComponent },
  { path: 'csr-view/:id', component: CsrViewerComponent },
  
  { path: 'csr-view/:id', component: CsrViewerComponent },
  { path: 'new-certificate', component: NewCertificateComponent},
  { path: 'csr-submit', component: CSRSubmitComponent},
  { path: 'csr-requests', component: CsrRequestViewerComponent},
  { path: 'login-ca', component: LoginCAComponent},
   {path: 'all-certificates', component: AllCertificatesComponent},
   {path: 'ceritificate-view/:id', component: CertificateViewComponent},
  
  //Disabled AuthGuard
  /*
  { path: 'csr-view/:id', component: CsrViewerComponent , canActivate: [AuthCAGuard]},
  { path: 'new-certificate', component: NewCertificateComponent, canActivate: [AuthCAGuard] },
  { path: 'csr-submit', component: CSRSubmitComponent, canActivate: [AuthMUGuard] },
  { path: 'csr-requests', component: CsrRequestViewerComponent, canActivate: [AuthCAGuard] },
  { path: 'login-ca', component: LoginCAComponent, canActivate: [AuthCAGuard]},
  */

  { path: '', component: HomepageComponent },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
