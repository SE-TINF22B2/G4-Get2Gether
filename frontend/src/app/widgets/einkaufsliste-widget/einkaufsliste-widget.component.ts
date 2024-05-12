import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BaseWidget} from "../../../model/common-widget";
import {Entry, EntryAddCommand, ShoppingWidget} from "../../../model/shoppinglist-widget";

@Component({
  selector: 'app-einkaufsliste-widget',
  templateUrl: './einkaufsliste-widget.component.html',
  styleUrl: './einkaufsliste-widget.component.scss'
})
export class EinkaufslisteWidgetComponent {
  @Input()
  eventId!: string;

  @Input({transform: (value: BaseWidget): ShoppingWidget => value as ShoppingWidget})
  widget!: ShoppingWidget;

  addAuftrag(addCommand:EntryAddCommand){
    return null;
  }
}
