import { Component } from '@angular/core';
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  static API_URL = environment.production ? 'http://localhost:4380/JavaEELab4-4.0/api' : "http://localhost:4380/JavaEELab4-4.0/api";
}
