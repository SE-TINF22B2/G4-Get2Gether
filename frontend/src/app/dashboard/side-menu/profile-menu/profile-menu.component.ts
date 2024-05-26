import {Component} from '@angular/core';
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../model/user";
import {MatDialog} from "@angular/material/dialog";
import {CreateEventDialogComponent} from "../../../create-event/create-event-dialog.component";
import {UserSettingsComponent} from "../../user-settings/user-settings.component";
import {environment} from "../../../../environment/environment";
import {EventService} from "../../../../services/event.service";
import {Router} from "@angular/router";
import {AppStateService} from "../../../../services/app-state.service";

@Component({
  selector: 'app-profile-menu',
  templateUrl: './profile-menu.component.html',
  styleUrl: './profile-menu.component.scss'
})
export class ProfileMenuComponent {

  constructor(
    public userService: UserService,
    public dialog: MatDialog,
    private service: EventService,
    private appStateService: AppStateService,
    private router: Router
  ) {}

  getUsername(user: User): string {
    if (user.guest)
      return "Gast";
    return [user.firstName, user.lastName].filter(x => x).join(" ");
  }

  openCreateEventDialog() {
    const dialogRef = this.dialog.open(CreateEventDialogComponent, {
      maxWidth: "800px",
      data: {}
    });
    dialogRef.afterClosed().subscribe(addCommand => {
      if(!addCommand) return;
      this.service.createEvent(addCommand).subscribe(event => {
        this.appStateService.doUpdateEventList.emit();
        this.router.navigate(['/dashboard', event.id]);
      })
    })
  }

  openUserSettings(){
    this.dialog.open(UserSettingsComponent, {
      width: "600px"
    });
  }

  logout() {
    window.open(environment.api+'/logout','_self');
  }
}
