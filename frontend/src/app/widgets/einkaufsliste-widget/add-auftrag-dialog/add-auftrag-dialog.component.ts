import {Component, ElementRef, input, NgZone, ViewChild} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {LocationAddCommand} from "../../../../model/map-widget";
import {MatSnackBar} from "@angular/material/snack-bar";
import {EntryAddCommand} from "../../../../model/shoppinglist-widget";


@Component({
  selector: 'app-add-auftrag-dialog',
  templateUrl: './add-auftrag-dialog.component.html',
  styleUrl: './add-auftrag-dialog.component.scss'
})
export class AddAuftragDialogComponent {
  private description:string|undefined;
  private amount:string|undefined;
  constructor(private dialogRef: MatDialogRef<AddAuftragDialogComponent>, private _snackbar:MatSnackBar) {
  }

  closeDialog() {
    const addCommand: EntryAddCommand = {
      description: "successfullyadded",
      amount: "3"
    };

    this.dialogRef.close(addCommand);
    this.showMessage("snack")

  }
  showMessage(messageToshow:string, snackBarClass:string="successfull"){
    this._snackbar.open(messageToshow, 'close!',{
      duration: 5000,
      panelClass:snackBarClass
    })
  }

}
