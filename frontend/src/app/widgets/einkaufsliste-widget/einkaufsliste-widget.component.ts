import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BaseWidget} from "../../../model/common-widget";
import {MapWidget} from "../../../model/map-widget";

@Component({
  selector: 'app-einkaufsliste-widget',
  templateUrl: './einkaufsliste-widget.component.html',
  styleUrl: './einkaufsliste-widget.component.scss'
})
export class EinkaufslisteWidgetComponent {
  @Input()
  eventId!: string;

  @Input({transform: (value: BaseWidget): MapWidget => value as MapWidget})
  widget!: MapWidget;

}
