import {APP_INITIALIZER, InjectionToken, LOCALE_ID, NgModule} from '@angular/core';
import localeDe from "@angular/common/locales/de";
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
import {EventListComponent} from './dashboard/side-menu/event-list/event-list.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {EventListItemComponent} from './dashboard/side-menu/event-list/event-list-item/event-list-item.component';
import {MatCardModule} from "@angular/material/card";
import {MatExpansionModule} from "@angular/material/expansion";
import {EventCreationComponent} from './eventcreation/event-creation.component';
import {EventpageComponent} from "./eventpage/eventpage.component";
import {MatNativeDateModule, MatRippleModule, NativeDateAdapter, DateAdapter} from "@angular/material/core";
import {EventSearchComponent} from './dashboard/side-menu/event-search/event-search.component';
import {UserSettingsComponent} from './dashboard/user-settings/user-settings.component';
import {UserSettingsItemComponent} from './dashboard/user-settings/user-settings-item/user-settings-item.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {EventBannerComponent} from './eventpage/event-banner/event-banner.component';
import {EventDescriptionComponent} from './eventpage/event-description/event-description.component';
import {WidgetsSectionComponent} from './eventpage/widgets-section/widgets-section.component';
import {WidgetContainerComponent} from './eventpage/widgets-section/widget-container/widget-container.component';
import {ScrollingModule} from "@angular/cdk/scrolling";
import {WidgetsBarComponent} from './eventpage/widgets-section/widgets-bar/widgets-bar.component';
import {MatTabsModule} from "@angular/material/tabs";
import { ParticipantsSidenavComponent } from './eventpage/participants-sidenav/participants-sidenav.component';
import { ParticipantCardComponent } from './eventpage/participants-sidenav/participant-card/participant-card.component';
import { ShoppingListWidgetComponent } from './widgets/shopping-list-widget/shopping-list-widget.component';
import { DefaultShoppingPageComponent } from './widgets/shopping-list-widget/default-shopping-page/default-shopping-page.component';
import { AddEntryDialogComponent } from './widgets/shopping-list-widget/add-entry-dialog/add-entry-dialog.component';
import {RouterOutlet} from "@angular/router";
import {
  MatDatepicker, MatDatepickerActions, MatDatepickerApply, MatDatepickerCancel,
  MatDatepickerToggle,
  MatDateRangeInput, MatDateRangePicker,
  MatEndDate,
  MatStartDate
} from "@angular/material/datepicker";
import { ExpenseSplitWidgetComponent } from './widgets/expense-split-widget/expense-split-widget.component';
import { CreateEditExpenseEntryDialogComponent } from './widgets/expense-split-widget/create-edit-expense-entry-dialog/create-edit-expense-entry-dialog.component';
import {MatSelectModule} from "@angular/material/select";
import { ExpenseEntryCardComponent } from './widgets/expense-split-widget/expense-entry-card/expense-entry-card.component';
import { DeleteEntryConfirmationDialogComponent } from './widgets/expense-split-widget/delete-entry-confirmation-dialog/delete-entry-confirmation-dialog.component';
import { EditEntryDialogComponent } from './widgets/shopping-list-widget/edit-entry-dialog/edit-entry-dialog.component';
import { ShoppingListEntryListItemComponent } from './widgets/shopping-list-widget/shopping-list-entry-list-item/shopping-list-entry-list-item.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {AddWidgetDialogComponent} from "./eventpage/widgets-section/add-widget-dialog/add-widget-dialog.component";
import { ExpenseDebtOverviewComponent } from './widgets/expense-split-widget/expense-debt-overview/expense-debt-overview.component';
import { CarpoolWidgetComponent } from './widgets/carpool-widget/carpool-widget.component';
import { DefaultCarpoolPageComponent } from './widgets/carpool-widget/default-carpool-page/default-carpool-page.component';
import { AddCarpoolDialogComponent } from './widgets/carpool-widget/add-carpool-dialog/add-carpool-dialog.component';
import { CarpoolCardItemComponent } from './widgets/carpool-widget/carpool-card-item/carpool-card-item.component';

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
    EventpageComponent,
    DefaultEventPageComponent,
    EventListComponent,
    EventListItemComponent,
    EventCreationComponent,
    EventSearchComponent,
    UserSettingsComponent,
    UserSettingsItemComponent,
    EventDescriptionComponent,
    EventBannerComponent,
    WidgetsSectionComponent,
    WidgetContainerComponent,
    WidgetsBarComponent,
    ParticipantsSidenavComponent,
    ParticipantCardComponent,
    ExpenseSplitWidgetComponent,
    CreateEditExpenseEntryDialogComponent,
    ExpenseEntryCardComponent,
    DeleteEntryConfirmationDialogComponent,
    ShoppingListWidgetComponent,
    DefaultShoppingPageComponent,
    AddEntryDialogComponent,
    EditEntryDialogComponent,
    ShoppingListEntryListItemComponent,
    CarpoolWidgetComponent,
    DefaultCarpoolPageComponent,
    AddCarpoolDialogComponent,
    ExpenseDebtOverviewComponent,
    CarpoolCardItemComponent,
    AddWidgetDialogComponent
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
    MatMenuModule,
    MatRippleModule,
    FormsModule,
    ScrollingModule,
    MatTabsModule,
    RouterOutlet,
    MatDatepicker,
    MatEndDate,
    MatStartDate,
    MatDateRangeInput,
    MatDatepickerToggle,
    MatDateRangePicker,
    MatDatepickerActions,
    MatDatepickerCancel,
    MatDatepickerApply,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatSelectModule,
    MatCheckboxModule
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
    },
    {
      provide: DateAdapter,
      useClass: NativeDateAdapter
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
