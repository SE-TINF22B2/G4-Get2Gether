import { Component } from '@angular/core';
import {MatDialogModule} from "@angular/material/dialog";

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrl: './user-settings.component.scss'
})
export class UserSettingsComponent {
  darkColors :string[] = ["dark", "dark-ry", "dark-bv"];
  lightColors:string[]=["light", "light-gy", "light-bg"]

}


