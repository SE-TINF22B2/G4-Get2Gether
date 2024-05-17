import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {EntryCommand} from "../../../../model/shoppinglist-widget";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-add-eintrag-dialog',
  templateUrl: './add-eintrag-dialog.component.html',
  styleUrl: './add-eintrag-dialog.component.scss'
})
export class AddEintragDialogComponent implements OnInit{
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AddEintragDialogComponent>,
  ) {
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      description: ['', Validators.required],
      amount: [null]
    });

  }

  closeDialog() {
    if(this.form.valid) {
      const addCommand: EntryCommand = {
        description: this.form.value.description,
        amount: this.form.value.amount
      }
      this.dialogRef.close(addCommand);
    }
  }
}
