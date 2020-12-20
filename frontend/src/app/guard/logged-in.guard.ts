import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import {AuthService} from "../services/auth.service";

@Injectable()
export class LoggedInAuthGuard implements CanActivate {

  constructor(private _authService: AuthService, private _router: Router) { }

  canActivate(): boolean {
    if (this._authService.isLoggedIn()) {
      this._router.navigate(['/main']);
      return false
    } else {
      return true
    }
  }
}
