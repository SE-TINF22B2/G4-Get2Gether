import {Component, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../../services/user.service";
import {EinkaufslisteWidgetService} from "../../../../services/widgets/einkaufsliste-widget.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {User} from "../../../../model/user";
import {Entry} from "../../../../model/shoppinglist-widget";

@Component({
  selector: 'app-edit-eintrag-dialog',
  templateUrl: './edit-eintrag-dialog.component.html',
  styleUrl: './edit-eintrag-dialog.component.scss'
})
export class EditEintragDialogComponent {
  form!: FormGroup;
  eventId: string;
  widgetId: string;
  entry: Entry;
  constructor(
    public userService: UserService,
    private fb: FormBuilder,
    private service: EinkaufslisteWidgetService,
    private dialogRef: MatDialogRef<EditEintragDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {eventId: string, widgetId: string, entry:Entry},
    private _snackbar:MatSnackBar) {
    this.eventId = data.eventId
    this.widgetId = data.widgetId
    this.entry = data.entry
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      description: [this.entry.description, Validators.required],
      amount: [this.entry.amount]
    });

  }
  getUsername(user:User): string {
    return [user.firstName, user.lastName].filter(x => x).join(" ");
  }

  closeDialog() {
    if(this.form.valid) {
      this.entry.description = this.form.get('description')?.value
      this.entry.amount = this.form.get('amount')?.value
      this.service.editEntry(this.eventId, this.widgetId, this.entry).subscribe({
        next: response => {
          this.showMessage("Eintrag angelegt")
          this.dialogRef.close();
        },
        error: error => {
          console.error('Error:', error);
          this.showMessage("Fehler beim Anlegen", "error")
        }
      });
    }
    this.showMessage("Bitte gib eine Beschreibung an", "error")
    //was passiert, wenn keine Eingabe
  }
  showMessage(messageToshow:string, snackBarClass:string="successfull"){
    this._snackbar.open(messageToshow, 'schließen',{
      duration: 5000,
      panelClass:snackBarClass
    })
  }
}
