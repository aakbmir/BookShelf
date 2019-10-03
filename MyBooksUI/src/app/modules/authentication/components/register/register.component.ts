import { Component, OnInit } from '@angular/core';
import { User } from "../../User";
import { AuthenticationService } from "../../authentication.service";
import { MatSnackBar } from '@angular/material';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user: User;
  constructor(
    private authService: AuthenticationService,
    private snackbar: MatSnackBar,
    private router: Router) {
    this.user = new User();
  }

  login()
  {
    this.router.navigate(["/login"]);
  }
  register() {
    if(this.user.username != null && this.user.password != null && this.user.email != null)
    {
      let regex = new RegExp("[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$");
      var regexMatch  = regex.test(this.user.email);
     if(regexMatch)
     {
        this.authService.registerUser(this.user).subscribe(
        data => {
        if (data.status === 201) {
          this.snackbar.open("Registration completed successfully !! ", " ", {
            duration: 1500,
            panelClass: ['success-snackbar']
          });
        }
        this.router.navigate(["/login"]);
      },
      error => {
        if (error.status === 409) {
          const errorMsg = error.error.message;
          this.snackbar.open(errorMsg, " ", {
            duration:1500,
            panelClass: ['error-snackbar']
          });
        }
      }
    );
  }
  else
  {
    const errorMsg = "Invalid Email Format !!    (example@email.com)";
    this.snackbar.open(errorMsg, " ", {
      duration:2500,
      panelClass: ['error-snackbar']  
    });
  }
  }
  else
  {
    const errorMsg = "All the fields are mandatory !!";
    this.snackbar.open(errorMsg, " ", {
      duration:1500,
      panelClass: ['error-snackbar']
    });
  }
  }
  ngOnInit() {
  }

}
