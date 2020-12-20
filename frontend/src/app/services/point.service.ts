import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AppComponent} from "../app.component";
import {Points} from "../model/response/model.point";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PointService {

  constructor(private http: HttpClient) { }

  getPoints(): Observable<Points> {
    return this.http.get<Points>(AppComponent.API_URL+"/points/get-all", httpOptions);
  }

  addPoint(point: any): Observable<any> {
    return this.http.post(AppComponent.API_URL + '/points/add', point, httpOptions);
  }

  removePoints(): Observable<any> {
    return this.http.get(AppComponent.API_URL + '/points/remove-all', httpOptions);
  }
}
