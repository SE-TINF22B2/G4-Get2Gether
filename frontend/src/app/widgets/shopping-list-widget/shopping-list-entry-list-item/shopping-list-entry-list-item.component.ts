import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Entry} from "../../../../model/shoppinglist-widget";
import {Event} from "../../../../model/event";
import {SimpleUser} from "../../../../model/user";

@Component({
  selector: 'app-einkaufliste-eintrag-list-item',
  templateUrl: './shopping-list-entry-list-item.component.html',
  styleUrl: './shopping-list-entry-list-item.component.scss'
})
export class ShoppingListEntryListItemComponent {
  @Input() item!: Entry;
  @Input() eventData!: Event;
  @Output() onEdit = new EventEmitter();
  @Output() onDelete = new EventEmitter();

  get buyer(): SimpleUser | undefined {
    if (!this.item.buyerId) {
      return undefined;
    }
    return this.eventData.participants.find(p => p.id === this.item.buyerId);
  }

}
