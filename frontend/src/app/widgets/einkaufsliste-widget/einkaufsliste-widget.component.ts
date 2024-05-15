import {Component, Input} from '@angular/core';
import {BaseWidget} from "../../../model/common-widget";
import {Entry, ShoppingWidget} from "../../../model/shoppinglist-widget";
import {AddEintragDialogComponent} from "./add-eintrag-dialog/add-eintrag-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {EditEintragDialogComponent} from "./edit-eintrag-dialog/edit-eintrag-dialog.component";

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

  addEintrag() {
    const dialogRef = this.dialog.open(AddEintragDialogComponent, {data: { eventId: this.eventId, widgetId: this.widget.id },
      width: "400px"});
    dialogRef.afterClosed().subscribe(entries => {
      if (entries) {
        //load entries
      }
    });
  }
  editEntryDialog(entry:Entry){
    const dialogRef = this.dialog.open(EditEintragDialogComponent, {data: { eventId: this.eventId, widgetId: this.widget.id , entry:entry},
      width: "400px"});
    dialogRef.afterClosed().subscribe(entries => {
      if (entries) {
        //load entries
      }
    });
  }

  get entries(): number {
    return this.widget.entries.length;
  }
}
