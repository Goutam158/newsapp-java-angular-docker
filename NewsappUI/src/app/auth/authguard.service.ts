import { Injectable } from '@angular/core';
import { CanActivate,Router } from '@angular/router';
import { AuthService } from './authservice.service';

@Injectable()
export class AuthguardService implements CanActivate {
 

  constructor(private _router: Router, private _authService : AuthService) { }
  canActivate(){
    if(this._authService.isLoggedIn()){
        return true;
    }
    this._router.navigateByUrl('/login');
    return false;
}
}
