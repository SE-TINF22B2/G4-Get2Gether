import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from "@angular/cdk/layout";
import {UserService} from "../services/user.service";
import {NgOptimizedImage} from "@angular/common";
import {SplashscreenComponent} from "./splashscreen/splashscreen.component";

function initializeAppFactory(userService: UserService) {
  return () => {
    userService.init();
  };
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    LayoutModule,
    AppRoutingModule,
    HttpClientModule,
    NgOptimizedImage,
    SplashscreenComponent
  ],
  providers: [{
    provide: APP_INITIALIZER,
    useFactory: initializeAppFactory,
    deps: [UserService],
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
