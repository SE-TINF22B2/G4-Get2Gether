import {Component, Input} from '@angular/core';
import {SimpleUser} from "../../../../model/event";

@Component({
  selector: 'app-participant-card',
  templateUrl: './participant-card.component.html',
  styleUrl: './participant-card.component.scss'
})
export class ParticipantCardComponent {
  @Input()
  user!: SimpleUser

  @Input()
  isOwner!: boolean;
}
