import { TestBed } from '@angular/core/testing';

import { AuthenticationService } from './authentication.service';
import { HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

const testdata={
  username:"Test",
  password:"TestPass",
  email:"TestEmail"
};

describe('AuthenticationService', () => {
  let authService: AuthenticationService;
  let httpTestingController: HttpTestingController;
  const testForRegister = "http://localhost:8086/favouriteservice/api/v1/userbookservice/register";
  const testForLogin = "http://localhost:8086/userservice/api/v1/userservice/login";

  beforeEach(() => {
    
    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers:[AuthenticationService]
    });
    
    authService = TestBed.get(AuthenticationService);
    httpTestingController = TestBed.get(HttpTestingController)
  });

  it('should be created', () => {
    
    expect(authService).toBeTruthy();
  });

  it("#registerUser() should fetch proper response from Http Call",() => {
    authService.registerUser(testdata).subscribe(res => {
      console.log(res);
      expect(res.body).toBe(testdata);
    });
    const httpMockReq = httpTestingController.expectOne(testForRegister);
    expect(httpMockReq.request.method).toBe('POST');
    expect(httpMockReq.request.url).toEqual(testForRegister);
  });

  it("#login() should fetch proper response from Http Call",() => {
    authService.loginUser(testdata).subscribe(res => {
      console.log(res);
      expect(res.body).toBe(testdata);
    });
    const httpMockReq = httpTestingController.expectOne(testForLogin);
    expect(httpMockReq.request.method).toBe('POST');
    expect(httpMockReq.request.url).toEqual(testForLogin);
  });
});
