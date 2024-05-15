import {Component, Input} from '@angular/core';
import {Entry} from "../../../../model/shoppinglist-widget";

@Component({
  selector: 'app-einkaufliste-eintrag-list-item',
  templateUrl: './einkaufliste-eintrag-list-item.component.html',
  styleUrl: './einkaufliste-eintrag-list-item.component.scss'
})
export class EinkauflisteEintragListItemComponent {
  @Input()item!: Entry;

}
