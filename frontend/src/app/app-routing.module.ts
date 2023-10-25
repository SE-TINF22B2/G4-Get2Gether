import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {AuthGuardService} from "./AuthGuardService";

const routes: Routes = [
  {
    path: "login", component: LoginComponent
  },
  {
    path: "dashboard", component: DashboardComponent
    , canActivate: [AuthGuardService]
  },
  {path: "*", redirectTo: "dashboard"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
