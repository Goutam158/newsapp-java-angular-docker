import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup } from '@angular/forms';
import {AuthService} from '../authservice.service';
import {UserModel} from '../../models/UserModel';
import {Router} from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  userModel : UserModel ;
  loginForm: FormGroup;
  isLoggedIn:boolean=false;
  errorMessage:string;
  
  constructor(private _authservice: AuthService,private _router: Router) { }

  ngOnInit() {
    this.createForm();
    this.isLoggedIn = this._authservice.isLoggedIn();
  }

  createForm(): void{
    this.loginForm = new FormGroup({
      userId: new FormControl(''),
     password: new FormControl('')
    }
    );
  }
  validateUser(): void {
    if (this.assignFormValue().email === undefined || this.assignFormValue().email.trim() === '') {
      this.errorMessage = 'User name cannot be blank';
    }
    else if (this.assignFormValue().password === 'undefined' || this.assignFormValue().password.trim() === '') {
      this.errorMessage = 'Password cannot be blank';

    }
    else {
      this._authservice.authenticateUser(this.assignFormValue()).subscribe(token => {
        this._authservice.setToken(token);
        this._authservice.setUser(this.assignFormValue().email);
        this.isLoggedIn = true;
        this.clear();
        this._router.navigateByUrl('/search');

      }, errorResp => {
        this.errorMessage = JSON.parse(errorResp.error)['message'];
        this.isLoggedIn = false;
      });
    }
  }

  assignFormValue():UserModel {
   this.userModel = new UserModel();
   this.userModel.email= this.loginForm.controls.userId.value;
   this.userModel.password = this.loginForm.controls.password.value;
   return this.userModel;
  }

  clear(){
    this.loginForm.controls.userId=undefined;
    this.loginForm.controls.password=undefined;
    this.errorMessage=undefined;
  }
  onKeyPress(){
    this.errorMessage = null;
  }

}
