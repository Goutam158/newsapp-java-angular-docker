import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { InterceptorService} from './interceptor.service';
import { RouterModule, Routes } from '@angular/router'
import {
  MatToolbarModule, MatIconModule, 
  MatButtonModule,MatDividerModule,
  MatCardModule, MatTabsModule,MatDialogModule
} from '@angular/material';
import { NgMatSearchBarModule } from 'ng-mat-search-bar';

import { DashboardComponent } from '../core/dashboard/dashboard.component';
import { FavouriteComponent } from './favourite/favourite.component';
import { SearchComponent } from '../core/search/search.component';
import { AuthguardService } from '../auth/authguard.service';
import { FormsModule } from '@angular/forms';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';


const coreRoutes: Routes = [
  { path: 'dashboard', component: DashboardComponent ,canActivate:[AuthguardService]},
  { path: 'search', component: SearchComponent ,canActivate:[AuthguardService]},
  { path: 'favourite', component: FavouriteComponent ,canActivate:[AuthguardService]}
]
@NgModule({
  declarations: [
    DashboardComponent,
    FavouriteComponent,
    SearchComponent,
    ConfirmationDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    NgMatSearchBarModule,
    MatToolbarModule,
    MatIconModule, MatButtonModule,
    MatDividerModule,MatCardModule,
    MatTabsModule,MatDialogModule, 
    RouterModule.forChild(coreRoutes)
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: InterceptorService, multi: true }
    ],
    entryComponents:[ConfirmationDialogComponent],
  exports: [MatToolbarModule, MatButtonModule, MatIconModule, MatTabsModule]
})
export class CoreModule { }
