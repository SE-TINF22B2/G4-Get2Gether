import {APP_INITIALIZER, InjectionToken, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {HttpClient, HttpClientJsonpModule, HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from "@angular/cdk/layout";
import {UserService} from "../services/user.service";
import {NgOptimizedImage} from "@angular/common";
import {SplashscreenComponent} from "./splashscreen/splashscreen.component";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {SideMenuComponent} from "./dashboard/side-menu/side-menu.component";
import {DashboardContentComponent} from "./dashboard/dashboard-content/dashboard-content.component";
import {MatDividerModule} from "@angular/material/divider";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MapsWidgetComponent} from './widgets/maps-widget/maps-widget.component';
import {GoogleMap, MapMarker} from "@angular/google-maps";
import {environment} from "../environment/environment";
import {catchError, map, Observable, of, shareReplay} from "rxjs";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {AddLocationDialogComponent} from './widgets/maps-widget/add-location-dialog/add-location-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import { ProfileMenuComponent } from './dashboard/side-menu/profile-menu/profile-menu.component';
import {DefaultEventPageComponent} from "./eventpage/default-event-page/default-event-page.component";

function initializeAppFactory(userService: UserService) {
  return () => {
    userService.init();
  };
}

export const MAP_LOADED = new InjectionToken<Observable<boolean>>('GoogleMapsLoaded');

function loadMapApi(httpClient: HttpClient) {
  return httpClient.jsonp("https://maps.googleapis.com/maps/api/js?libraries=places&key=" + environment.googleMapsApiKey, "callback").pipe(
    shareReplay({bufferSize: 1, refCount: true}),
    map(() => true),
    catchError(e => {
      console.log("Failed to load Google Maps Library:", e);
      return of(false);
    })
  );
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    SplashscreenComponent,
    SideMenuComponent,
    DashboardContentComponent,
    MapsWidgetComponent,
    AddLocationDialogComponent,
    ProfileMenuComponent,
    DefaultEventPageComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    LayoutModule,
    AppRoutingModule,
    HttpClientModule,
    HttpClientJsonpModule,
    NgOptimizedImage,
    MatSidenavModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatDividerModule,
    MatProgressBarModule,
    MatFormFieldModule,
    MatInputModule,
    MatListModule,
    MatDialogModule,
    GoogleMap,
    MapMarker
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeAppFactory,
      deps: [UserService],
      multi: true
    },
    {
      provide: MAP_LOADED,
      useFactory: loadMapApi,
      deps: [HttpClient]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
