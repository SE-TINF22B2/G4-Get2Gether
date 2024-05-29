import { Component } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-delete-car-confirmation-dialog',
  templateUrl: './delete-car-confirmation-dialog.component.html',
  styleUrl: './delete-car-confirmation-dialog.component.scss'
})
export class DeleteCarConfirmationDialogComponent {

  constructor(private dialogRef: MatDialogRef<DeleteCarConfirmationDialogComponent>) {
  }

  confirmDelete() {
    this.dialogRef.close(true);
  }

}
