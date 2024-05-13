import {Component} from '@angular/core';
import {UserService} from "../../../services/user.service";
import {ColorMode} from "../../../model/user";

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrl: './user-settings.component.scss'
})
export class UserSettingsComponent {
  lightColors = [ColorMode.LIGHT, ColorMode.WATER, ColorMode.SUNSET, ColorMode.GRASSLAND];
  darkColors = [ColorMode.DARK, ColorMode.DEVELOPER, ColorMode.AUTUMN];

  isLoading = false;

  constructor(public userService: UserService) {
  }

  selectColorMode(colorMode: ColorMode) {
    this.isLoading = true;
    this.userService.applyThemeAttribute(colorMode);
    this.userService.updateTheme(colorMode).subscribe(() => {
      this.isLoading = false;
    });
  }
}
