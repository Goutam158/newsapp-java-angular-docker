import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { UserModel } from '../models/UserModel';
import * as jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private TOKEN: string = "news-app-token";
  private endpoint: string = "http://localhost:9090/userservice/api/v1";

  constructor(private _http: HttpClient) {
  }

  authenticateUser(userModel: UserModel): Observable<any> {
    return this._http.post(this.endpoint + '/login', userModel, { responseType: 'text' });
  }
  signup(userModel:UserModel):Observable<any>{
    return this._http
    .post(this.endpoint +'/signup',userModel, {responseType : 'text'});
}
  setToken(token: string) {
    localStorage.setItem(this.TOKEN, token);
  }
  setUser(userId: string) {
    localStorage.setItem('currentUser', userId);
  }
  getToken(): string {
    return localStorage.getItem(this.TOKEN);
  }
  getUser(): string {
    return localStorage.getItem('currentUser');
  }
  logout() {
    localStorage.removeItem(this.TOKEN);
    localStorage.removeItem('currentUser');
  }
  isLoggedIn(): boolean {
    let token = localStorage.getItem(this.TOKEN)
    if (token === undefined || token === null) {
      return false;
    }
    return this.validateToken(token);
  }

  private validateToken(token: string): boolean {
    const date: Date = this.getTokenExpirationDate(token);
    if (date === undefined || date === null) {
      return false;
    }
    return !(date.valueOf() > new Date().valueOf());
  }

  private getTokenExpirationDate(token: string): Date {
    const decode = jwt_decode(token);
    if (decode.iat === undefined) {
      return null;
    }
    const date = new Date(0);
    date.setUTCSeconds(decode.iat);
    return date;
  }
}
