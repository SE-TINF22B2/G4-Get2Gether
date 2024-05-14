import {Component} from '@angular/core';
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../model/user";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {EventCreationComponent} from "../../../eventcreation/event-creation.component";
import {UserSettingsComponent} from "../../user-settings/user-settings.component";
import {environment} from "../../../../environment/environment";

@Component({
  selector: 'app-profile-menu',
  templateUrl: './profile-menu.component.html',
  styleUrl: './profile-menu.component.scss'
})
export class ProfileMenuComponent {

  constructor(
    public userService: UserService,
    public dialog: MatDialog
  ) {}

  getUsername(user: User): string {
    if (user.guest)
      return "Gast";
    return [user.firstName, user.lastName].filter(x => x).join(" ");
  }

  openCreateEventDialog() {
    this.dialog.open(EventCreationComponent, {maxWidth: "800px"});
  }

  openUserSettings(){
    this.dialog.open(UserSettingsComponent, {
      width: "600px"
    });
  }

  logout() {
    console.log(environment.api);
    window.open(environment.api+'/logout','_self');
  }
}
