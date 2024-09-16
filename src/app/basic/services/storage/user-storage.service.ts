// import { Injectable } from '@angular/core';
import { Injectable } from "@angular/core";

const TOKEN = 's_token';
const USER = 's_user';

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {

  constructor() { }

  public saveToken(token: string): void {
    console.log('Saving token:', token);
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN, token);
  }

  static getToken(): string {
    return localStorage.getItem(TOKEN);
  }

  public saveUser(user): void {
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  // static getUser(): any | null {
  //   const userString = localStorage.getItem(USER);
  //   try {
  //     return userString ? JSON.parse(userString) : null;
  //   } catch (e) {
  //     console.error('Error parsing user from localStorage', e);
  //     return null;
  //   }
  // }

  static getUser(): any {
        return JSON.parse(localStorage.getItem(USER));
    }

  static getUserId(): string {
    const user = this.getUser();
    if (user === null ) {
      return ''; 
    }
    return user.userId; 
  }

  static getUserRole(): string {
    const user = this.getUser();
    if (user === null ) {
      return '';
     
    }
    return user.role; 
  }

  static isClientLoggedIn(): boolean {
    if (this.getToken() === null) {
      return false;
    }
    const role:string = this.getUserRole();
    return role == 'CLIENT';
  }

  static isCompanyLoggedIn(): boolean {
    if (this.getToken() === null) {
      return false;
    }
    const role = this.getUserRole();
    return role == 'COMPANY';
  }

  static signOut():void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }
}
