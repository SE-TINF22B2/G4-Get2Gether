import {Component, Input} from '@angular/core';
import {Event} from "../../../model/event";

@Component({
  selector: 'app-event-banner',
  templateUrl: './event-banner.component.html',
  styleUrl: './event-banner.component.scss'
})
export class EventBannerComponent {
  @Input() eventData: Event | undefined ;
}
