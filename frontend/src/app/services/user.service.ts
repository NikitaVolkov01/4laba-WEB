import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from "../model/request/model.user";
import { Observable } from 'rxjs';
import {AppComponent} from "../app.component";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getCurrentProfile(): Observable<any> {
    return this.http.get<any>(AppComponent.API_URL+"/current", httpOptions);
  }
}
