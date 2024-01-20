import {Component, OnInit} from '@angular/core';
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{
  isPhonePortrait = false;

  constructor(public breakpointObserver: BreakpointObserver) {}

  ngOnInit() {
      this.breakpointObserver.observe(Breakpoints.HandsetPortrait)
        .subscribe(result => {
          this.isPhonePortrait = result.matches;
      })
  }

  onClickLogin(){
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  }
}
