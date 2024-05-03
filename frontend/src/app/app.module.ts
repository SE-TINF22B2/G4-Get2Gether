import {APP_INITIALIZER, InjectionToken, LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {HttpClient, HttpClientJsonpModule, HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LayoutModule} from "@angular/cdk/layout";
import {UserService} from "../services/user.service";
import {NgOptimizedImage, registerLocaleData} from "@angular/common";
import {SplashscreenComponent} from "./splashscreen/splashscreen.component";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MAT_ICON_DEFAULT_OPTIONS, MatIconModule} from "@angular/material/icon";
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
import {ProfileMenuComponent} from './dashboard/side-menu/profile-menu/profile-menu.component';
import {MatTooltipModule, TooltipComponent} from "@angular/material/tooltip";
import {MatMenuModule} from "@angular/material/menu";
import {DefaultEventPageComponent} from "./eventpage/default-event-page/default-event-page.component";
import {SpecificEventPageComponent} from './eventpage/specific-event-page/specific-event-page.component';
import {EventListComponent} from './dashboard/side-menu/event-list/event-list.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {EventListItemComponent} from './dashboard/side-menu/event-list/event-list-item/event-list-item.component';
import {MatCardModule} from "@angular/material/card";
import {MatExpansionModule} from "@angular/material/expansion";

import localeDe from "@angular/common/locales/de";

registerLocaleData(localeDe);

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
    SpecificEventPageComponent,
    EventListComponent,
    EventListItemComponent,
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
    TooltipComponent,
    MatTooltipModule,
    MatFormFieldModule,
    MatInputModule,
    MatListModule,
    MatDialogModule,
    GoogleMap,
    MapMarker,
    MatProgressSpinnerModule,
    MatCardModule,
    MatExpansionModule,
    MatMenuModule
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
    },
    {
      provide: LOCALE_ID,
      useValue: "de-DE"
    },
    {
      provide: MAT_ICON_DEFAULT_OPTIONS,
      useValue: {
        fontSet: "material-icons-round"
      }
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
