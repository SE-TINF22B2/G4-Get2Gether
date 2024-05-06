import { Component } from '@angular/core';
import {MatDialogModule} from "@angular/material/dialog";

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrl: './user-settings.component.scss'
})
export class UserSettingsComponent {
  colors :string[] = ["dark", "light", "modern"];

}


