import { Component, OnInit } from '@angular/core';
import { NavbarModule, WavesModule } from 'angular-bootstrap-md'
import {TabMenuModule} from 'primeng/tabmenu';
import {MenuItem} from 'primeng/primeng';
import { ViewEncapsulation } from '@angular/core';
import {AuthService} from '../../auth/authservice.service'
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  encapsulation: ViewEncapsulation.Emulated
})
export class DashboardComponent implements OnInit {

  constructor(private _authService:AuthService,private _snackBar : MatSnackBar) { }

  user :String;
  navLinks: any[];

  ngOnInit() {
     this.user = this._authService.getUser().substring(0,this._authService.getUser().lastIndexOf("@"));
      this.navLinks = [
          {label: 'Search News',link:'../search'},
          {label: 'My WishLists', link:'../favourite'}          
      ];
  }

  logOut():void{
    this._authService.logout();
    this._snackBar.open('User successfully logged out',null,{duration : 2000,});
  }
   
}
