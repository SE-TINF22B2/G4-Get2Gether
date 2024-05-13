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

  get themeName(): string {
    switch (this.colorMode) {
      case ColorMode.LIGHT:
        return "Hell";
      case ColorMode.DARK:
        return "Dunkel";
      case ColorMode.WATER:
        return "Wasser";
      case ColorMode.GRASSLAND:
        return "Weide";
      case ColorMode.SUNSET:
        return "Sonnenuntergang";
      case ColorMode.DEVELOPER:
        return "Entwickler";
      case ColorMode.AUTUMN:
        return "Herbst";
    }
  }

}
