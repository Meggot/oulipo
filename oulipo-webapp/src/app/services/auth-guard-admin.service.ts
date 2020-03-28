import { ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';
import { Injectable } from '@angular/core';

import { AppState } from '../store/app.reducers';
import { Store } from '@ngrx/store';

import {map, take} from 'rxjs/operators'

@Injectable()
export class AuthAdminGuard implements CanActivate {

    constructor(private store: Store<AppState>) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.store.select('auth','role').pipe(
            map((role:number) => {
                if (role == 2) {
                    return true;
                }
            }),
            take(1)
        );
    }
}