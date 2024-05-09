import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BaseWidget, WidgetType} from "../../../../model/common-widget";

@Component({
  selector: 'app-widgets-bar',
  templateUrl: './widgets-bar.component.html',
  styleUrl: './widgets-bar.component.scss'
})
export class WidgetsBarComponent {

  @Input()
  widgets!: BaseWidget[];

  @Input()
  selected: string | undefined;

  @Output()
  onClick = new EventEmitter<string>();

  getWidgetName(type: WidgetType): string {
    switch (type) {
      case WidgetType.SHOPPING_LIST:
        return "Einkaufsliste";
      case WidgetType.MAP:
        return "Karte";
      case WidgetType.EXPENSE_SPLIT:
        return "Ausgabenverteilung";
      case WidgetType.CARPOOL:
        return "Fahrgemeinschaft";
      case WidgetType.POLL:
        return "Umfrage";
      case WidgetType.GALLERY:
        return "Gallery";
      case WidgetType.WEATHER:
        return "Wetter";
      case WidgetType.COMMENT_SECTION:
        return "Kommentare";
    }
  }

}
