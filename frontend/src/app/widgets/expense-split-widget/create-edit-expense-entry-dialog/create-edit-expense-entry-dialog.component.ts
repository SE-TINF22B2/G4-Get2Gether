import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SimpleUser} from "../../../../model/user";
import {ExpenseEntry, ExpenseEntryAddCommand, ExpenseEntryUpdateCommand} from "../../../../model/expense-split-widget";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'app-create-edit-expense-split-dialog',
  templateUrl: './create-edit-expense-entry-dialog.component.html',
  styleUrl: './create-edit-expense-entry-dialog.component.scss'
})
export class CreateEditExpenseEntryDialogComponent {

  users: SimpleUser[];
  entry: ExpenseEntry | undefined;

  formGroup: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) data: { users: SimpleUser[], entry: ExpenseEntry | undefined },
    private dialogRef: MatDialogRef<CreateEditExpenseEntryDialogComponent>,
    public userService: UserService,
    fb: FormBuilder
  ) {
    this.users = data.users;
    this.entry = data.entry;

    this.formGroup = fb.group({
      description: new FormControl(
        this.entry?.description ?? "",
        Validators.required
      ),
      price: new FormControl(
        this.entry?.price ?? null,
        [Validators.required, Validators.min(0)]
      ),
      involvedUsers: new FormControl(
        this.entry?.involvedUsers?.map(u => u.user.id) ?? [],
        Validators.required
      )
    });
  }

  submit() {
    let data: ExpenseEntryAddCommand | ExpenseEntryUpdateCommand = {
      description: this.formGroup.value.description,
      price: this.formGroup.value.price,
      involvedUsers: this.formGroup.value.involvedUsers
    };
    this.dialogRef.close(data);
  }

  get isCreatingNewEntry(): boolean {
    return !this.entry;
  }

}
