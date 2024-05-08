import { Component } from '@angular/core';
import {MatDialogModule} from "@angular/material/dialog";

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrl: './user-settings.component.scss'
})
export class UserSettingsComponent {
  colors :string[] = ["dark", "light", "light-gy", "light-bg", "dark-ry", "dark-bv"];

  get lightColors(){
    return this.colors.filter(color=> color.startsWith('l'));
  }
  get darkColors(){
    return this.colors.filter(color=> color.startsWith('d'));
  }
}


