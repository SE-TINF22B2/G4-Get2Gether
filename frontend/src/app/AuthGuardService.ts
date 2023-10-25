import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {catchError, map, Observable, of, take, tap, using} from "rxjs";
import {SocialAuthService, SocialUser} from "@abacritt/angularx-social-login";


@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private router: Router,
              private socialAuthService: SocialAuthService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.socialAuthService.authState.pipe(
      take(1),
      map((socialUser: SocialUser) => {
        console.log("here")
        // if(!socialUser){
        //   this.router.navigateByUrl("/login")
        // }
        // this.appState.user.subscribe(user=>{
        //   console.log(user)
        //   return true;
        // })
        return !!socialUser;
      }),
      catchError((error) => {
        console.log("here 2")
        console.log(error);
        return of(false);
      })
    );
  }
}
