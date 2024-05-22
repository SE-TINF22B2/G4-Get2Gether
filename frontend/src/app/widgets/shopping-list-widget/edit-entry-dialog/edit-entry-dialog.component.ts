import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Entry, EntryCommand} from "../../../../model/shoppinglist-widget";

@Component({
  selector: 'app-edit-eintrag-dialog',
  templateUrl: './edit-entry-dialog.component.html',
  styleUrl: './edit-entry-dialog.component.scss'
})
export class EditEntryDialogComponent implements OnInit{
  form!: FormGroup;
  entry: Entry;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<EditEntryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { entry:Entry}
  ) {
    this.entry = data.entry
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      description: [this.entry.description, Validators.required],
      amount: [this.entry.amount]
    });
  }

  closeDialog() {
    if(this.form.valid) {
      const updateCommand: EntryCommand = {
        description: this.form.value.description,
        amount: this.form.value.amount
      };
      this.dialogRef.close(updateCommand);
    }
  }

}
