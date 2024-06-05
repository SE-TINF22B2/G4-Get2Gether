import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ExpenseEntry} from "../../../../model/expense-split-widget";
import {EventParticipant} from "../../../../model/user";
import {Event} from "../../../../model/event";
import {getUserNameForParticipant} from "../../../../utils/user.utils";

@Component({
  selector: 'app-expense-entry-card',
  templateUrl: './expense-entry-card.component.html',
  styleUrl: './expense-entry-card.component.scss'
})
export class ExpenseEntryCardComponent {

  @Input()
  entry!: ExpenseEntry;

  @Input()
  eventData!: Event;

  @Output()
  onDelete = new EventEmitter();

  @Output()
  onEdit = new EventEmitter();

  get getInvolvedUserNames(): string[] {
    return this.entry.involvedUsers.map(u => getUserNameForParticipant(u.user));
  }

  get isEveryoneInvolved(): boolean {
    const eventParticipantIds = this.eventData.participants.map(p => p.id);
    const involvedUserIds = this.entry.involvedUsers.map(u => u.user.id);
    return eventParticipantIds.length === involvedUserIds.length &&
      eventParticipantIds.every(id => involvedUserIds.includes(id));
  }

  get creator(): EventParticipant | undefined {
    return this.eventData.participants.find(p => p.id === this.entry.creatorId);
  }

  protected readonly getUserNameForParticipant = getUserNameForParticipant;
}
