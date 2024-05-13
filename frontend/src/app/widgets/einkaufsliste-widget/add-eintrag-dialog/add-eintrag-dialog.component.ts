import {Component, ElementRef, Input, input, NgZone, OnInit, ViewChild} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {LocationAddCommand} from "../../../../model/map-widget";
import {MatSnackBar} from "@angular/material/snack-bar";
import {EntryAddCommand} from "../../../../model/shoppinglist-widget";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {EinkaufslisteWidgetService} from "../../../../services/widgets/einkaufsliste-widget.service";
import {Router} from "@angular/router";
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../model/user";
import {ReplaySubject} from "rxjs";


@Component({
  selector: 'app-add-eintrag-dialog',
  templateUrl: './add-eintrag-dialog.component.html',
  styleUrl: './add-eintrag-dialog.component.scss'
})
export class AddEintragDialogComponent implements OnInit{
  form!: FormGroup;
  @Input() eventId!: string;
  @Input() widgetId!: string;
  constructor(
    public userService: UserService,
    private fb: FormBuilder,
    private service: EinkaufslisteWidgetService,
    private router: Router,
    private dialogRef: MatDialogRef<AddEintragDialogComponent>,
    private _snackbar:MatSnackBar) {
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
      this.service.addEntry(this.eventId, this.widgetId, this.form).subscribe({
        next: response => {
          //TODO: Routing zurück zur Widgetübersicht der Einkaufsliste + Snackbar anzeigen
          /*this.router.navigate()
          response.entrys;
          this.dialogRef.close(response.entrys);*/
          this.showMessage("Eintrag angelegt")
          this.dialogRef.close();
        },
        error: error => {
          console.error('Error:', error);
          this.showMessage("Fehler beim Anlegen", "error")
        }
      });
    }
    //was passiert, wenn keine Eingabe
  }
  showMessage(messageToshow:string, snackBarClass:string="successfull"){
    this._snackbar.open(messageToshow, 'close!',{
      duration: 5000,
      panelClass:snackBarClass
    })
  }
}
