import {Component, Input} from '@angular/core';
import {Entry} from "../../../../model/shoppinglist-widget";
import {EditEintragDialogComponent} from "../edit-eintrag-dialog/edit-eintrag-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {UserService} from "../../../../services/user.service";
import {EinkaufslisteWidgetService} from "../../../../services/widgets/einkaufsliste-widget.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-einkaufliste-eintrag-list-item',
  templateUrl: './einkaufliste-eintrag-list-item.component.html',
  styleUrl: './einkaufliste-eintrag-list-item.component.scss'
})
export class EinkauflisteEintragListItemComponent {
  @Input() item!: Entry;
  @Input() eventId!: string;
  @Input() widgetId!: string;

  constructor(
    private dialog: MatDialog,
    private userService: UserService,
    private service: EinkaufslisteWidgetService,
    private _snackbar: MatSnackBar
  ) {
  }

  editEntryDialog(){
    const dialogRef = this.dialog.open(EditEintragDialogComponent, {width: "400px"});
    dialogRef.afterClosed().subscribe(addCommand => {
      if (addCommand) {
        //TODO: Logik zum speichern von bearbeiteten Einträgen fehlt evtl noch im Backend
        //load entries
        /*this.service.editEntry(this.eventId, this.widgetId, this.entry).subscribe({
          next: response => {
            this.showMessage("Eintrag angelegt")
            this.dialogRef.close();
          },
          error: error => {
            console.error('Error:', error);
            this.showMessage("Fehler beim Anlegen", "error")
          }
        });*/
      }
    });
  }

  loadUsername() {
    this.userService.fetchUserById(this.item.buyerId).subscribe({

    });
  }

  showMessage(messageToshow:string, snackBarClass:string="successfull"){
    this._snackbar.open(messageToshow, 'schließen',{
      duration: 5000,
      panelClass:snackBarClass
    })
  }

}
