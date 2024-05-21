import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Event} from "../../../../model/event";
import {BaseWidget} from "../../../../model/common-widget";
import {Entry, EntryCheckCommand, EntryCommand, ShoppingWidget} from "../../../../model/shoppinglist-widget";
import {MatDialog} from "@angular/material/dialog";
import {EinkaufslisteWidgetService} from "../../../../services/widgets/einkaufsliste-widget.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AddEntryDialogComponent} from "../../shopping-list-widget/add-entry-dialog/add-entry-dialog.component";
import {EditEntryDialogComponent} from "../../shopping-list-widget/edit-entry-dialog/edit-entry-dialog.component";
import {MatCheckboxChange} from "@angular/material/checkbox";

@Component({
  selector: 'app-carpool-card-item',
  templateUrl: './carpool-card-item.component.html',
  styleUrl: './carpool-card-item.component.scss'
})
export class CarpoolCardItemComponent {

  @Input()
  eventData!: Event; b

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
    const dialogRef = this.dialog.open(AddEntryDialogComponent, {width: "400px"});
    dialogRef.afterClosed().subscribe(addCommand => {
      if (addCommand) {
        this.addEntry(addCommand);
      }
    });
  }

  addEntry(addCommand: EntryCommand) {
    if (addCommand) {
      this.service.addEntry(this.eventData.id, this.widget.id, addCommand).subscribe({
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

  openEditEntryDialog(entry: Entry) {
    const dialogRef = this.dialog.open(EditEntryDialogComponent, {
      data: {entry: entry},
      width: "400px"
    });
    dialogRef.afterClosed().subscribe(updateCommand => {
      if (updateCommand) {
        this.service.editEntry(this.eventData.id, this.widget.id, entry.id, updateCommand).subscribe({
          next: response => {
            this.onWidgetUpdated.emit(response);
            this.showMessage("Eintrag bearbeitet")
          },
          error: error => {
            console.error('Error:', error);
            this.showMessage("Fehler beim Bearbeiten", "error")
          }
        });
      }
    });
  }

  onCheckboxChange(item: Entry, event: MatCheckboxChange) {
    const checkCommand: EntryCheckCommand = {
      checked: event.checked
    };
    this.service.checkEntry(this.eventData.id, this.widget.id, item, checkCommand).subscribe({
      next: response => {
        this.onWidgetUpdated.emit(response);
      },
      error: error => {
        console.error('Error:', error);
      }
    });
  }

  deleteEntry(entry: Entry) {
    this.service.deleteEntry(this.eventData.id, this.widget.id, entry.id).subscribe({
      next: response => {
        this.onWidgetUpdated.emit(response);
        this.showMessage("Eintrag gelöscht")
      },
      error: error => {
        console.error('Error:', error);
        this.showMessage("Fehler beim Löschen", "error")
      }
    });
  }

  get entriesCount(): number {
    return this.widget.entries.length;
  }

  get entries() {
    return this.widget.entries;
  }

  private showMessage(messageToShow: string, snackBarClass: string = "successfull") {
    this._snackbar.open(messageToShow, 'schließen', {
      duration: 5000,
      panelClass: snackBarClass
    });
  }
}
