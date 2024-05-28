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
    return this.events.filter(event => {
      if (new Date(event.date).getTime() >= now)
        return true;
      return event.endDate && new Date(event.endDate).getTime() >= now;
    });
  }

  get archivedEvents() {
    const now = Date.now();
    return this.events.filter(event => {
      if (event.endDate && new Date(event.endDate).getTime() < now)
        return true;
      return new Date(event.date).getTime() < now;
    });
  }
}
