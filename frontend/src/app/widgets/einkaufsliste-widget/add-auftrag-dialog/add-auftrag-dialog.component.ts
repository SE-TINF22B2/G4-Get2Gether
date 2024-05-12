import {Component, NgZone} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {LocationAddCommand} from "../../../../model/map-widget";


@Component({
  selector: 'app-add-auftrag-dialog',
  templateUrl: './add-auftrag-dialog.component.html',
  styleUrl: './add-auftrag-dialog.component.scss'
})
export class AddAuftragDialogComponent {
  constructor(private dialogRef: MatDialogRef<AddAuftragDialogComponent>) {
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
