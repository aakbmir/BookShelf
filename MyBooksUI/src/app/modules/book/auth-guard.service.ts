import { Injectable } from '@angular/core';
import { Input } from '@angular/core';
import { AuthenticationService} from '../authentication/authentication.service';
import { Router} from '@angular/router';
import { CanActivate } from '@angular/router/src/interfaces';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{
  @Input()
  
  loginFlag: boolean;
  constructor(private authService: AuthenticationService, private route:Router) {

   }

   canActivate()
   {
    
     if(this.authService.isTokenExpired())
     {
       this.loginFlag=true;
       return true;
     }
     this.loginFlag=false;
     this.route.navigate(['/login']);
     return false;
   }
}
