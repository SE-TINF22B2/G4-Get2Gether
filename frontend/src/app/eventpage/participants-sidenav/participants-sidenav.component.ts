import {Component, Input, OnInit} from '@angular/core';
import {Event} from "../../../model/event";
import {SimpleUser} from "../../../model/user";

@Component({
  selector: 'app-participants-sidenav',
  templateUrl: './participants-sidenav.component.html',
  styleUrl: './participants-sidenav.component.scss'
})
export class ParticipantsSidenavComponent implements OnInit {
  @Input()
  eventData!: Event;

  owner: SimpleUser | undefined;

  ngOnInit(): void {
    this.owner = this.eventData.participants.find((user) => user.id === this.eventData.creatorId)
  }

  allParticipantsExceptOwner(): SimpleUser[] {
    return this.eventData.participants.filter((user) => user.id !== this.eventData.creatorId)
  }

}
