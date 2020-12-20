import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  authService: AuthService;

  constructor(authService: AuthService) {
    this.authService = authService;
  }


  ngOnInit(): void {
  }

}
