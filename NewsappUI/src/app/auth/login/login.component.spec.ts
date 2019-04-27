import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClient,HttpHandler } from '@angular/common/http';
import { LoginComponent } from './login.component';
import { AuthService } from '../authservice.service';
import { Router} from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule, MatButtonModule, MatCardModule } from '@angular/material';
import {FormsModule} from '@angular/forms';


 
describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers :[
        AuthService,
        HttpClient,
        HttpHandler,
        {
          provide: Router,
          useClass: class{ navigate = jasmine.createSpy("navigate");}
        }
      ],
      imports: [
        BrowserAnimationsModule,
        MatInputModule, 
        MatButtonModule, 
        MatCardModule, 
        FormsModule],
      declarations: [ LoginComponent ]
    })
    .compileComponents();
  }));
    

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
