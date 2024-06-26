import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {EntryCommand} from "../../../../model/shoppinglist-widget";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-eintrag-dialog',
  templateUrl: './add-entry-dialog.component.html',
  styleUrl: './add-entry-dialog.component.scss'
})
export class AddEntryDialogComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AddEntryDialogComponent>,
  ) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      description: ['', Validators.required],
      amount: [null]
    });
  }

  closeDialog() {
    if (this.form.valid) {
      const addCommand: EntryCommand = {
        description: this.form.value.description,
        amount: this.form.value.amount
      }
      this.dialogRef.close(addCommand);
    }
  }
}
