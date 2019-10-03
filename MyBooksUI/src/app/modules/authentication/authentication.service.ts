import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
export const USER_NAME = 'username';
export const TOKEN_NAME = 'jwt_token';
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService
{
  private SpringRegisterEndPoint: string;
  private SpringSaveEndPoint: string;
  private SpringLoginEndPoint: string;

  constructor(private httpClient: HttpClient) { 
    this.SpringRegisterEndPoint="http://localhost:8086/favouriteservice/api/v1/userbookservice/register";
    this.SpringLoginEndPoint="http://localhost:8086/userservice/api/v1/userservice/login";
  }

  registerUser(newUser)
  {
    const url = this.SpringRegisterEndPoint;
    return this.httpClient.post(url,newUser, {observe: "response"});
  }

  loginUser(newUser)
  {
    const url = this.SpringLoginEndPoint;
    sessionStorage.setItem(USER_NAME, newUser.username);
    return this.httpClient.post(url,newUser, {observe:"response"});
  }

  getToken()
  {
    return localStorage.getItem(TOKEN_NAME);
  }

  isTokenExpired(token?: string):boolean
  {
    if(localStorage.getItem(TOKEN_NAME))
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  logout()
  {
    sessionStorage.removeItem(USER_NAME);
    sessionStorage.clear();
    localStorage.removeItem(TOKEN_NAME);
    localStorage.clear();
    console.log("logged out");
  }
}
