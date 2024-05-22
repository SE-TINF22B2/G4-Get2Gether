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
    return this.events.filter(event => new Date(event.date).getTime() >= now ? true : event.endDate ? new Date(event.endDate).getTime() >= now : false);
  }

  get archivedEvents() {
    const now = Date.now();
    return this.events.filter(event => event.endDate ? new Date(event.endDate).getTime() < now : new Date(event.date).getTime() < now);
  }
}
