import { Component } from '@angular/core';
import {SocialAuthService, SocialUser} from "@abacritt/angularx-social-login";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  user?: SocialUser;
  loggedIn: boolean = false;

  constructor(private authService: SocialAuthService, private router: Router) { }

  ngOnInit() {
    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = (user != null);
      this.router.navigateByUrl("/dashboard")
      console.log(this.user)
    });
  }
}
