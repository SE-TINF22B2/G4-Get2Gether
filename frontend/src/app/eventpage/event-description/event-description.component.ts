import {Component, Input} from '@angular/core';
import {Event} from "../../../model/event";

@Component({
  selector: 'app-event-description',
  templateUrl: './event-description.component.html',
  styleUrl: './event-description.component.scss'
})
export class EventDescriptionComponent {
  @Input() eventData: Event | undefined ;
}
