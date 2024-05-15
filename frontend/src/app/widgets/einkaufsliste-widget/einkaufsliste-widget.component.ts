import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BaseWidget} from "../../../model/common-widget";
import {Entry, EntryAddCommand, ShoppingWidget} from "../../../model/shoppinglist-widget";
import {AddEintragDialogComponent} from "./add-eintrag-dialog/add-eintrag-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {EinkaufslisteWidgetService} from "../../../services/widgets/einkaufsliste-widget.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatCheckboxChange} from "@angular/material/checkbox";

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

  @Output()
  onWidgetUpdated = new EventEmitter<ShoppingWidget>();

  constructor(
    private dialog: MatDialog,
    private service: EinkaufslisteWidgetService,
    private _snackbar: MatSnackBar
  ) {
  }

  openAddEntryDialog() {
    const dialogRef = this.dialog.open(AddEintragDialogComponent, {width: "400px"});
    dialogRef.afterClosed().subscribe(addCommand => {
      if (addCommand) {
        this.addEntry(addCommand);
      }
    });
  }

  addEntry(addCommand: EntryAddCommand) {
    if(addCommand) {
      this.service.addEntry(this.eventId, this.widget.id, addCommand).subscribe({
        next: response => {
          this.onWidgetUpdated.emit(response);
          this.showMessage("Eintrag angelegt")
        },
        error: error => {
          console.error('Error:', error);
          this.showMessage("Fehler beim Anlegen", "error")
        }
      });
    }
  }

  get entries(): number {
    return this.widget.entries.length;
  }

  get eintraege() {
    console.log(this.widget.entries);
    return this.widget.entries;
  }

  showMessage(messageToshow:string, snackBarClass:string="successfull"){
    this._snackbar.open(messageToshow, 'schließen',{
      duration: 5000,
      panelClass: snackBarClass
    })
  }

  onCheckboxChange(item: Entry, event: MatCheckboxChange) {
    this.service.setBuyerId(this.eventId,this.widget.id, item, event.checked).subscribe({
      next: response => {
        this.onWidgetUpdated.emit(response);
      },
      error: error => {
        console.error('Error:', error);
      }
    });
  }
}
