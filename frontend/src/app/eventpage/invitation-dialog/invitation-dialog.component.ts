import {Component, EventEmitter, Inject, Output} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Event} from "../../../model/event";
import {environment} from "../../../environment/environment";
import {EventService} from "../../../services/event.service";
import {tap} from "rxjs";

@Component({
  selector: 'app-invitation-dialog',
  templateUrl: './invitation-dialog.component.html',
  styleUrl: './invitation-dialog.component.scss'
})
export class InvitationDialogComponent {

  eventData: Event;
  isLoading = false;

  @Output()
  onEventUpdated = new EventEmitter<Event>();

  constructor(@Inject(MAT_DIALOG_DATA) data: { eventData: Event }, private eventService: EventService) {
    this.eventData = data.eventData;
  }

  generateInvitationLink() {
    if (this.isLoading) return;
    this.eventService.generateInvitationLink(this.eventData.id).pipe(
      tap(() => this.isLoading = false),
    ).subscribe(event => {
      this.eventData = event;
      this.onEventUpdated.emit(event);
    });
  }

  get hasInvitationLink(): boolean {
    return !!this.eventData.invitationLink;
  }

  get invitationLink(): string {
    const link = this.eventData.invitationLink;
    if (!link) {
      return "Kein Einladungslink vorhanden.";
    }
    return `${environment.api}/event/invitation/${link}`;
  }

}
