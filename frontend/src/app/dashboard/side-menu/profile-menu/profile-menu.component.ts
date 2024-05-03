import {Component} from '@angular/core';
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'app-profile-menu',
  templateUrl: './profile-menu.component.html',
  styleUrl: './profile-menu.component.scss'
})
export class ProfileMenuComponent {

  constructor(
    public userService: UserService,
  ) {}

}
