import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpInterceptor
} from '@angular/common/http';
import { TokenStorageService } from "../services/token-storage.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private tokenService: TokenStorageService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): any {
    let authRequest = request;
    const token = this.tokenService.getToken();
    if (token != null) {
      authRequest = request.clone({headers: request.headers.set('Authorization', 'Bearer ' + token)})
    }
    return next.handle(authRequest);
  }
}
