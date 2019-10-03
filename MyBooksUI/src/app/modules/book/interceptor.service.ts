import { Injectable } from '@angular/core';
import { HttpInterceptor } from '@angular/common/http';
import { AuthenticationService } from '../authentication/authentication.service';

import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InterceptorService implements HttpInterceptor {

  constructor(private auth: AuthenticationService) { }


intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
  if(request.url.search('http://openlibrary.org') === -1)
  {
  request = request.clone({
    setHeaders: {
      Authorization: `Bearer ${this.auth.getToken()}`
    }
  });
}

  return next.handle(request);
}
}