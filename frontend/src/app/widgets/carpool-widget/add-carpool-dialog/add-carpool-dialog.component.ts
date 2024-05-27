import {Component, Inject} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UserService} from "../../../../services/user.service";
import {Car, CarAddCommand, CarUpdateCommand} from "../../../../model/carpool-widget";

@Component({
  selector: 'app-add-carpool-dialog',
  templateUrl: './add-carpool-dialog.component.html',
  styleUrl: './add-carpool-dialog.component.scss'
})
export class AddCarpoolDialogComponent {
  form: FormGroup;
  car: Car | undefined;

  constructor(
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) data: { car: Car | undefined},
    public userService: UserService,
    private dialogRef: MatDialogRef<AddCarpoolDialogComponent>,
  ) {
    this.car = data.car;

    this.form = fb.group({
      driver: new FormControl(
        this.car?.driverId ?? "",
        Validators.required
      ),
      adress: new FormControl(
        this.car?.driverAdress ?? ""
      ),
      seats: new FormControl(
        this.car?.anzahlPlaetze ?? null,
        Validators.required
      )
    });
  }

  submit() {
    let data: CarAddCommand| CarUpdateCommand = {
      driverAdress: this.form.value.adress,
      anzahlPlaetze: this.form.value.seats
    };
    this.dialogRef.close(data);
  }

  get isCreatingNewCar(): boolean {
    return !this.car;
  }

}
