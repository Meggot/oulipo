import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Store } from '@ngrx/store';
import { map, take, switchMap } from 'rxjs/operators';
import { Injectable } from '@angular/core';

@Injectable()
export class AuthInterceptor implements HttpInterceptor{

    constructor(private store: Store<any>) {}
    
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{
        return this.store.select('auth', 'token').pipe(
            take(1),
            switchMap((token) => {
                if (token !== null) {
                    req = req.clone({headers: req.headers.set('Authorization', token)});
                }
                console.log('Intercepted,', req)
                return next.handle(req);
            })
        )
    }
}