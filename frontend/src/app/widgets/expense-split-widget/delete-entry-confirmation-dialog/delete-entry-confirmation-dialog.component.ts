import { Component } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-delete-entry-confirmation-dialog',
  templateUrl: './delete-entry-confirmation-dialog.component.html',
  styleUrl: './delete-entry-confirmation-dialog.component.scss'
})
export class DeleteEntryConfirmationDialogComponent {

  constructor(private dialogRef: MatDialogRef<DeleteEntryConfirmationDialogComponent>) {
  }

  confirmDelete() {
    this.dialogRef.close(true);
  }

}
