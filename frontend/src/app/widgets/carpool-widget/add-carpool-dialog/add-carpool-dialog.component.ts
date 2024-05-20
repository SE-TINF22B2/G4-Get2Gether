import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SimpleUser} from "../../../../model/user";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ExpenseEntry} from "../../../../model/expense-split-widget";
import {UserService} from "../../../../services/user.service";

@Component({
  selector: 'app-add-carpool-dialog',
  templateUrl: './add-carpool-dialog.component.html',
  styleUrl: './add-carpool-dialog.component.scss'
})
export class AddCarpoolDialogComponent implements OnInit{
  form!: FormGroup;

  constructor(
    private fb: FormBuilder
  ) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      driver: ['', Validators.required],
      adress: [null],
      seats: ['', Validators.required]
    });
  }
  addCarpool(){
    console.log('addCarpool');
    //TODO: POST mit driver, adress und seats an /event/{eventId}/widgets/carpool/

    //TODO: Snackbar auslösen
  }

}
