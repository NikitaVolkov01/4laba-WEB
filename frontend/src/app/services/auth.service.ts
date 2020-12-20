import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AppComponent} from "../app.component";
import {User} from "../model/request/model.user";
import {TokenStorageService} from "./token-storage.service";


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient, private tokenService: TokenStorageService) { }

  isLoggedIn() {
    return this.tokenService.getToken() !== null;
  }

  getUser() {
    return this.tokenService.getUser();
  }

  login(user: User): Observable<any> {
    return this.http.post(AppComponent.API_URL + '/login', user, httpOptions);
  }

  register(user : User): Observable<any> {
    return this.http.post(AppComponent.API_URL + '/register', user, httpOptions);
  }

  logOut() {
    this.tokenService.signOut();
  }
}
