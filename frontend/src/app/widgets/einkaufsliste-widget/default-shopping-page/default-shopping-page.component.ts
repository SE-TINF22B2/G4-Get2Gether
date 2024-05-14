import {Component, Input} from '@angular/core';
import {AddLocationDialogComponent} from "../../maps-widget/add-location-dialog/add-location-dialog.component";
import {AddEintragDialogComponent} from "../add-eintrag-dialog/add-eintrag-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {EinkaufslisteWidgetComponent} from "../einkaufsliste-widget.component";
import {EntryAddCommand, ShoppingWidget} from "../../../../model/shoppinglist-widget";
import {BaseWidget} from "../../../../model/common-widget";

@Component({
  selector: 'app-default-shopping-page',
  templateUrl: './default-shopping-page.component.html',
  styleUrl: './default-shopping-page.component.scss'
})
export class DefaultShoppingPageComponent {
  @Input()
  eventId!: string;

  @Input({transform: (value: BaseWidget): ShoppingWidget => value as ShoppingWidget})
  widget!: ShoppingWidget;
  constructor(private dialog: MatDialog, private root:EinkaufslisteWidgetComponent) {
  }
  openAddShoppingDialog() {
    const dialogRef = this.dialog.open(AddEintragDialogComponent, {data: { eventId: this.eventId, widgetId: this.widget.id },
      width: "400px"});
    dialogRef.afterClosed().subscribe(entries => {
      if (entries) {
        //load entries
      }
    });
  }
}
