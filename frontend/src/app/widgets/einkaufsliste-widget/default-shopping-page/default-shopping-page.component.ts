import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AddEintragDialogComponent} from "../add-eintrag-dialog/add-eintrag-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {EinkaufslisteWidgetComponent} from "../einkaufsliste-widget.component";
import {ShoppingWidget} from "../../../../model/shoppinglist-widget";
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

  @Output()
  onWidgetUpdated = new EventEmitter<ShoppingWidget>();
  //TODO: Output funktioniert noch nicht ganz

  constructor(
    private dialog: MatDialog,
    private root: EinkaufslisteWidgetComponent
  ) {
  }

  openAddEntryDialog() {
    const dialogRef = this.dialog.open(AddEintragDialogComponent, {width: "400px"});
    dialogRef.afterClosed().subscribe(addCommand => {
      if (addCommand) {
        this.root.addEntry(addCommand);
      }
    });
  }
}
