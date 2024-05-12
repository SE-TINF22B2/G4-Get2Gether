import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ColorMode} from "../../../../model/user";

@Component({
  selector: 'app-user-settings-item',
  templateUrl: './user-settings-item.component.html',
  styleUrl: './user-settings-item.component.scss'
})
export class UserSettingsItemComponent {

  @Input()
  colorMode!: ColorMode;

  @Input()
  isSelected = false;

  @Output()
  onSelect = new EventEmitter();

}
