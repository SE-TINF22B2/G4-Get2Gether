import {Component, Input} from '@angular/core';
import {EventOverview} from "../../../../../model/event";

@Component({
  selector: 'app-event-list-item',
  templateUrl: './event-list-item.component.html',
  styleUrl: './event-list-item.component.scss'
})
export class EventListItemComponent {

  @Input()
  event!: EventOverview;

}
