
import { Component } from '@angular/core';
import { AuthService } from '../authservice.service';
import { UserModel } from '../../models/UserModel';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent  {

  errorMessage:string;
  message:string;
  userModel:UserModel;
  
  nameRegex:RegExp = new RegExp('^[a-zA-Z]+$');
  emailRegex:RegExp = new RegExp('^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$');
  passwordRegex:RegExp = new RegExp('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$');
  constructor(private _authService:AuthService,
              private _snackBar : MatSnackBar,
              private _router:Router) 
              {
                this.userModel = new UserModel();
               }

  signup(){
    if(this.validateForm()){
       this._authService.signup(this.userModel)
        .subscribe(
          res=>{
            this.clear();
            this.message=res;
            this._router.navigateByUrl('/login');
            this._snackBar.open(`User signup successful`,null,{duration : 2000,});
          },
          errorResp=>{
            this.message=undefined;
            this.errorMessage = JSON.parse(errorResp.error)['message'];
          }
        );
    }
  }

  private validateForm():boolean{
    if(this.userModel.firstName == undefined || this.userModel.firstName == null || this.userModel.firstName.trim() ==''){
      this.errorMessage = 'First Name is blank';
      return false;
    }
    if(!this.nameRegex.test(this.userModel.firstName)){
      this.errorMessage = 'First Name must contain only english alphabets';
      return false;  
    }
    
    if(this.userModel.lastName == undefined || this.userModel.lastName == null || this.userModel.lastName.trim() ==''){
      this.errorMessage = 'Last Name is blank';
      return false;
    }
    if(!this.nameRegex.test(this.userModel.lastName)){
      this.errorMessage = 'Last Name must contain only english alphabets';
      return false;  
    }
    if(this.userModel.email == undefined || this.userModel.email == null || this.userModel.email.trim() ==''){
      this.errorMessage = 'Email is blank';
      return false;
    }
    if(!this.emailRegex.test(this.userModel.email)){
      this.errorMessage = 'Malformed email id';
      return false;  
    }
    if(this.userModel.password == undefined || this.userModel.password == null || this.userModel.password.trim() ==''){
      this.errorMessage = 'Password is blank';
      return false;
    }
    if(!this.passwordRegex.test(this.userModel.password)){
      this.errorMessage = 'Password must contain Minimum eight characters, at least one upper case and lower case letter, one number, one special charecter';
      return false;  
    }
    if(this.userModel.retypePassword == undefined || this.userModel.retypePassword == null || this.userModel.retypePassword.trim() ==''){
      this.errorMessage = 'Retype password is blank';
      return false;
    }
    if( this.userModel.password != this.userModel.retypePassword){
      this.errorMessage = 'Password and Retype password is not matching';
      return false;
    }
    return true;
  }

  clear(){
    this.errorMessage = undefined;
    this.message = undefined;
    this.userModel = new UserModel();
  }

  onKeyPress(){
    this.errorMessage = null;
  }
 

}
