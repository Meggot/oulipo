import { ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate, CanLoad } from '@angular/router';
import { Injectable } from '@angular/core';

import { AppState } from '../store/app.reducers';
import { Store } from '@ngrx/store';

import {map, take} from 'rxjs/operators'


@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private store: Store<AppState>) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.store.select('auth','authenticated').pipe(
            map((authenticated:boolean) => {
                return authenticated; 
            }),
            take(2)
        );
    }
}