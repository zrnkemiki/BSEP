import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpBackend } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { LoginDTO } from '../modelDTO/loginDTO';

import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { LoginCaDTO } from '../modelDTO/loginCaDTO';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  //private userUrl = "http://localhost:8080/api/login";
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  //private http: HttpClient;

  constructor(private http : HttpClient, private router: Router) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  //Registracija TO-DO
  register(user: User) {
    //return this.http.post(`${environment.apiUrl}/register`, user);
}

  public get currentUserValue(): User {
    let user: User = new User();
    if (localStorage.getItem('currentUser')) {
      user.deserialize(JSON.parse(localStorage.getItem('currentUser')));
      if (user.idUser != this.currentUserSubject.value.idUser) {
        this.currentUserSubject.next(user);
      }
    }
    return this.currentUserSubject.value;
  }

  public get currentUserStatus(): String {
    if (this.currentUserValue) {
      return this.currentUserValue.status;
    }
    return "ADMIN";
  }

  login(loginDto: LoginDTO) {
    return this.http.post<any>(`https://localhost:9003/login`, loginDto)
      .pipe(map(userDTO => {
        if (userDTO && userDTO.jwttoken) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          let user: User = new User().deserialize(userDTO);
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
        }
        return userDTO;
      })).subscribe(
        (data) => { alert("Successful!") },
        error => { alert("Wrong username or password!") }
      );

      
  }

  loginCA(loginCaDTO: LoginCaDTO){
    this.http.post<any>('https://localhost:9003/login/loginCA', loginCaDTO)
    .subscribe(
      data => {
        alert("Successfuly sent");
      }
    )
}

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(["/login"]);
  }

}
