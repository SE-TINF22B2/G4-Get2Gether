import { Component } from '@angular/core';
import {SocialAuthService, SocialUser} from "@abacritt/angularx-social-login";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'frontend';
  user?: SocialUser;
  loggedIn: boolean = false;

  constructor(private http: HttpClient ,private authService: SocialAuthService, private router: Router) { }

  ngOnInit() {
    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = (user != null);
      this.router.navigateByUrl("/dashboard")
      console.log(this.user)
      this.onclick();
    });
  }

  onclick(){
    this.http.post("http://localhost:8080/login", { token: this.user?.idToken}, {withCredentials: true}).subscribe(result =>{
      console.log(result);
    })
  }
}
