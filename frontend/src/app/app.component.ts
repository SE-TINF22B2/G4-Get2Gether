import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AppStateService} from "../services/app-state";
import {LoginService} from "../services/loginService";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'frontend';

  constructor(private http: HttpClient, private router: Router, private appState: AppStateService, private loginService: LoginService) {
  }

  ngOnInit() {
    this.loginService.getUserData().subscribe({
      next: user => {
        console.log(user)
        this.appState.user.next(user);
      }, error: () => {
        this.router.navigateByUrl("/login");
      }
    });
  }

}
