import {Component, Input, OnInit} from '@angular/core';
import {Event, SimpleUser} from "../../../model/event";
import {using} from "rxjs";

@Component({
  selector: 'app-participants-sidenav',
  templateUrl: './participants-sidenav.component.html',
  styleUrl: './participants-sidenav.component.scss'
})
export class ParticipantsSidenavComponent implements OnInit{
  @Input()
  eventData!: Event;
  protected readonly using = using;

  owner: SimpleUser | undefined;

  ngOnInit(): void {
     this.owner = this.eventData.participants.find((user) => user.id === this.eventData.creatorId)
  }

  allParticipantsExceptOwer(): SimpleUser[] {
    return this.eventData.participants.filter((user) => user.id !== this.eventData.creatorId)
  }


}
