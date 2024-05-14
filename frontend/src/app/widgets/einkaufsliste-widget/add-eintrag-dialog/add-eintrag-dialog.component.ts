import {Component, Inject, OnInit} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {EntryAddCommand} from "../../../../model/shoppinglist-widget";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EinkaufslisteWidgetService} from "../../../../services/widgets/einkaufsliste-widget.service";
import {Router} from "@angular/router";
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../model/user";

@Component({
  selector: 'app-add-eintrag-dialog',
  templateUrl: './add-eintrag-dialog.component.html',
  styleUrl: './add-eintrag-dialog.component.scss'
})
export class AddEintragDialogComponent implements OnInit{
  form!: FormGroup;
  eventId: string;
  widgetId: string;
  constructor(
    public userService: UserService,
    private fb: FormBuilder,
    private service: EinkaufslisteWidgetService,
    private dialogRef: MatDialogRef<AddEintragDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {eventId: string, widgetId: string},
    private _snackbar:MatSnackBar) {
      this.eventId = data.eventId
      this.widgetId = data.widgetId
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      description: ['', Validators.required],
      amount: [null]
    });

  }
  getUsername(user:User): string {
    return [user.firstName, user.lastName].filter(x => x).join(" ");
  }

  closeDialog() {
    if(this.form.valid) {
      const entryAddCommand: EntryAddCommand = {
        description: this.form.get('descrption')?.value,
        amount: this.form.get('amount')?.value || "",
      }
      this.service.addEntry(this.eventId, this.widgetId, entryAddCommand).subscribe({
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
