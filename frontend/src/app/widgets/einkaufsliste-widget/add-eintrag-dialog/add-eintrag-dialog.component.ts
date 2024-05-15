import {Component, Inject, OnInit} from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {EntryAddCommand} from "../../../../model/shoppinglist-widget";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EinkaufslisteWidgetService} from "../../../../services/widgets/einkaufsliste-widget.service";
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../model/user";

@Component({
  selector: 'app-add-eintrag-dialog',
  templateUrl: './add-eintrag-dialog.component.html',
  styleUrl: './add-eintrag-dialog.component.scss'
})
export class AddEintragDialogComponent implements OnInit{
  form!: FormGroup;

  constructor(
    public userService: UserService,
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
/*  getUsername(user:User): string {
    return [user.firstName, user.lastName].filter(x => x).join(" ");
  }*/

  closeDialog() {
    if(this.form.valid) {
      const addCommand: EntryAddCommand = {
        description: this.form.value.description,
        amount: this.form.value.amount
      }
      this.dialogRef.close(addCommand);
    }
  }
}
