import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BaseWidget} from "../../../model/common-widget";
import {Entry, EntryAddCommand, ShoppingWidget} from "../../../model/shoppinglist-widget";
import {AddEintragDialogComponent} from "./add-eintrag-dialog/add-eintrag-dialog.component";
import {MatDialog} from "@angular/material/dialog";

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

  constructor(private dialog: MatDialog) {
  }

  openAddShoppingDialog() {
    const dialogRef = this.dialog.open(AddEintragDialogComponent, {width: "400px"});
    dialogRef.afterClosed().subscribe(entrys => {
      if (entrys) {
        //load entrys
      }
    });
  }
  addEintrag() {
    console.log(this.widget.entries.length != 0);

  }

  get entries(): number {
    return this.widget.entries.length;
  }
}
