import {Component, Input} from '@angular/core';
import {ExpenseEntry} from "../../../../model/expense-split-widget";
import {SimpleUser} from "../../../../model/user";
import {Event} from "../../../../model/event";

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

  get getInvolvedUserNames(): string[] {
    return this.entry.involvedUsers.map(u => [u.user.firstName, u.user.firstName].join(" "));
  }

  get isEveryoneInvolved(): boolean {
    const eventParticipantIds = this.eventData.participants.map(p => p.id);
    const involvedUserIds = this.entry.involvedUsers.map(u => u.user.id);
    return eventParticipantIds.length === involvedUserIds.length &&
      eventParticipantIds.every(id => involvedUserIds.includes(id));
  }

  get creator(): SimpleUser | undefined {
    return this.eventData.participants.find(p => p.id === this.entry.creatorId);
  }

}
