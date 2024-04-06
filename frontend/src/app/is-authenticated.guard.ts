import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {map} from "rxjs";
import {UserService} from "../services/user.service";

export const isAuthenticatedGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  return inject(UserService).user.pipe(map(user => {
    return !user ? router.parseUrl("/login") : true;
  }));
};
