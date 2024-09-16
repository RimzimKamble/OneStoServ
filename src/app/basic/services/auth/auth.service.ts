import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { catchError, map, Observable } from 'rxjs';
import { UserStorageService } from '../storage/user-storage.service';

const Basic_URL = 'http://localhost:8080/';
export const AUTH_HEADER ='authorization';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
    private userStorageService: UserStorageService) { }

  registerClient(signupRequestDTO:any): Observable<any>{
    return this.http.post(Basic_URL + "client/sign-up",signupRequestDTO);
  }
  
  registerCompany(signupRequestDTO:any): Observable<any>{
    return this.http.post(Basic_URL + "company/sign-up",signupRequestDTO);
  }
  
  login(username:string, password:string): Observable<any>{
    return this.http.post(Basic_URL + "authenticate" ,{username,password},{observe:'response'})
    .pipe(
      map((res: HttpResponse<any>)=>{
        console.log(res.body)
        this.userStorageService.saveUser(res.body);
        const tokenLength= res.headers.get(AUTH_HEADER)?.length;
        const bearertoken=res.headers.get(AUTH_HEADER)?.substring(7, tokenLength);
        console.log(bearertoken);
        this.userStorageService.saveToken(bearertoken);
        return res;
      }),
    )
  }
}
