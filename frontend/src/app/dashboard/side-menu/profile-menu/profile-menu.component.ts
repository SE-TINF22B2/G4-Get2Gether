import {Component} from '@angular/core';
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../model/user";

@Component({
  selector: 'app-profile-menu',
  templateUrl: './profile-menu.component.html',
  styleUrl: './profile-menu.component.scss'
})
export class ProfileMenuComponent {

  constructor(
    public userService: UserService,
  ) {}

  getUsername(user: User): string {
    if (user.guest)
      return "Gast";
    return [user.firstName, user.lastName].filter(x => x).join(" ");
  }

}
