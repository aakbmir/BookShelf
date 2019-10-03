import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../authentication/authentication.service';
import { Router } from '@angular/router';
import { AuthGuardService } from 'src/app/modules/book/auth-guard.service';
import { Observable } from 'rxjs/internal/Observable';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private router:Router, 
    private authGuardService:  AuthGuardService,
    private authService: AuthenticationService) { }

  logout()
  {
    this.authService.logout();  
    this.router.navigate(["/login"]);
  }
  ngOnInit() {
    this.authGuardService.canActivate;
  }


}