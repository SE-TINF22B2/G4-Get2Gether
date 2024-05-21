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
    private fb: FormBuilder,
    public userService: UserService,
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
    //TODO: Service

    //TODO: Snackbar auslösen
  }

}
