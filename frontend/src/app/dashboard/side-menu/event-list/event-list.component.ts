import {Component, Input} from '@angular/core';
import {EventOverview} from "../../../../model/event";

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.scss'
})
export class EventListComponent {

  @Input()
  events: EventOverview[] = [];

  get activeEvents() {
    const now = Date.now();
    return this.events.filter(event => new Date(event.date).getTime() >= now);
  }

  get archivedEvents() {
    const now = Date.now();
    return this.events.filter(event => new Date(event.date).getTime() < now);
  }

}
