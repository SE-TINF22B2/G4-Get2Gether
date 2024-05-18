import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Entry, ShoppingWidget} from "../../../../model/shoppinglist-widget";
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
export class EinkauflisteEintragListItemComponent implements OnInit, OnChanges{
  @Input() item!: Entry;
  @Input() eventId!: string;
  @Input() widgetId!: string;
  @Output() onWidgetUpdated = new EventEmitter<ShoppingWidget>();

  username: string | null = null;

  constructor(
    private dialog: MatDialog,
    private userService: UserService,
    private service: EinkaufslisteWidgetService,
    private _snackbar: MatSnackBar
  ) {
  }

  ngOnInit() {
    this.loadUsername();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['item'] && !changes['item'].firstChange) {
      this.loadUsername();
    }
  }

  loadUsername() {
    if (!this.item.buyerId) {
      this.username = null;  // Falls kein buyerId vorhanden ist, Username zurücksetzen
      return;
    }

    this.userService.fetchUserById(this.item.buyerId).subscribe({
      next: user => {
        this.username = [user.firstName, user.lastName].filter(x => x).join(" ");
      },
      error: error => console.error(error)
    });
  }

  editEntryDialog(){
    const dialogRef = this.dialog.open(EditEintragDialogComponent, { data: { entry: this.item},width: "400px"});
    dialogRef.afterClosed().subscribe(updateCommand => {
      if (updateCommand) {
        console.log(this.eventId);
        this.service.editEntry(this.eventId, this.widgetId, this.item.id, updateCommand).subscribe({
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

  showMessage(messageToshow:string, snackBarClass:string="successfull"){
    this._snackbar.open(messageToshow, 'schließen',{
      duration: 5000,
      panelClass:snackBarClass
    })
  }

  deleteEntry() {
    this.service.deleteEntry(this.eventId,this.widgetId,this.item.id).subscribe({
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
}
