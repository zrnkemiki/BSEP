import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { NewCertificateComponent } from './new-certificate/new-certificate.component';
import { HomepageComponent } from './homepage/homepage.component';
import { JwtInterceptor } from './helpers/jwt.interceptor';
import { ErrorInterceptor } from './helpers/error.interceptor';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthGuard } from './helpers/auth.guard';
import { LoginCAComponent } from './login-ca/login-ca.component';
import { CSRSubmitComponent } from './csrsubmit/csrsubmit.component';
import { CsrRequestViewerComponent } from './csr-request-viewer/csr-request-viewer.component';
import { CsrViewerComponent } from './csr-viewer/csr-viewer.component';
import { AuthCAGuard } from './helpers/auth.ca.guard';
import { AuthMUGuard } from './helpers/auth.mu.guard';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NewCertificateComponent,
    HomepageComponent,
    LoginCAComponent,
    CSRSubmitComponent,
    CsrRequestViewerComponent,
    CsrViewerComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule, 
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    AuthGuard, AuthCAGuard, AuthMUGuard,
    //{ provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
