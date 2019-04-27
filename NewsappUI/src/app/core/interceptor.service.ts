import { Injectable } from "@angular/core";
import { HttpRequest,HttpHandler,HttpInterceptor,HttpEvent} from '@angular/common/http';

import { AuthService } from "../auth/authservice.service";
import { Observable } from "rxjs";

@Injectable()
export class InterceptorService implements HttpInterceptor{
private TOKEN = 'news-app-token';
constructor(){}

intercept(request: HttpRequest<any>, next : HttpHandler): Observable<HttpEvent<any>>{
    request = request.clone({
        setHeaders: {
            Authorization: `Bearer ${localStorage.getItem(this.TOKEN)}`
        }
    });
    return next.handle(request);
}

}