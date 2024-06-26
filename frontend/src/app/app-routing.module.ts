import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {isAuthenticatedGuard} from "./is-authenticated.guard";
import {DefaultEventPageComponent} from "./eventpage/default-event-page/default-event-page.component";
import {EventpageComponent} from "./eventpage/eventpage.component";

const routes: Routes = [
  {
    path: "login", component: LoginComponent
  },
  {
    path: "dashboard",
    component: DashboardComponent,
    canActivate: [isAuthenticatedGuard],
    children: [
      {
        path: ":id",
        component: EventpageComponent
      },
      {
        path: "",
        component: DefaultEventPageComponent
      }
    ]
  },
  {path: "**", redirectTo: "dashboard"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
