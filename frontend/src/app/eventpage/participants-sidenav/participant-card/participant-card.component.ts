import {Component, Input} from '@angular/core';
import {EventParticipant} from "../../../../model/user";
import {getUserNameForParticipant} from "../../../../utils/user.utils";

@Component({
  selector: 'app-participant-card',
  templateUrl: './participant-card.component.html',
  styleUrl: './participant-card.component.scss'
})
export class ParticipantCardComponent {
  @Input()
  user!: EventParticipant

  @Input()
  isOwner!: boolean;
  protected readonly getUserNameForParticipant = getUserNameForParticipant;
}
