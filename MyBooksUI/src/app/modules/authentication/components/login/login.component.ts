import { Component, OnInit ,Input} from '@angular/core';
import { User } from "../../User";
import { AuthenticationService } from "../../authentication.service";
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';
export const TOKEN_NAME = 'jwt_token';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginFlag =false;

  user: User
  constructor(
    private authService: AuthenticationService,
    private snackbar: MatSnackBar,
    private router: Router
  ) {
    this.user=new User();
   }

   login()
   {
    if(this.user.username != null && this.user.password != null)
    {
     this.authService.loginUser(this.user).subscribe(data => {
       
       if(data)
       {
        this.loginFlag = true;
         localStorage.setItem(TOKEN_NAME, data.body["token"]);
         this.snackbar.open(data.body["message"], " ", {
           duration:1500,
           panelClass: ['success-snackbar']
         });
         this.loginFlag=true;
         
         this.router.navigate(["/Fiction"]);
       }
     },
    error =>
  {
    if(error.status === 404)
    {
      const errorMsg = error.error.message;
      this.snackbar.open(errorMsg, " ", {
        duration:1500,
        panelClass: ['error-snackbar']
        
      });
    }
  });
}
else
{
  const errorMsg = "Both Username and password is mandatory !!";
    this.snackbar.open(errorMsg, " ", {
      duration:1500,
      panelClass: ['error-snackbar']
    });
}

  
   }
  ngOnInit() {
  }

}
