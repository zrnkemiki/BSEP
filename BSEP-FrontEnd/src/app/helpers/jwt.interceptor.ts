import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        let currentUser = this.authenticationService.currentUserValue;
        if (currentUser && currentUser.jwttoken) {
            request = request.clone({
                setHeaders: {
                    "X-Auth-T" : `${currentUser.jwttoken}`,
                    "Content-Type" : 'application/json;charset=UTF-8'
                }
            });
        }
        return next.handle(request);
    }
}
