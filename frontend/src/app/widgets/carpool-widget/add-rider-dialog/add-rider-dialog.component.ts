import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../../services/user.service";
import {RiderAddCommand} from "../../../../model/carpool-widget";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-rider-dialog',
  templateUrl: './add-rider-dialog.component.html',
  styleUrl: './add-rider-dialog.component.scss'
})
export class AddRiderDialogComponent {

  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    public userService: UserService,
    private dialogRef: MatDialogRef<AddRiderDialogComponent>) {
    this.form = fb.group({
      pickupAdress: new FormControl(
        "", Validators.required
      )
    });
  }

  submit() {
    let data: RiderAddCommand = {
      pickupPlace: this.form.value.pickupAdress
    };
    this.dialogRef.close(data);
  }
}
