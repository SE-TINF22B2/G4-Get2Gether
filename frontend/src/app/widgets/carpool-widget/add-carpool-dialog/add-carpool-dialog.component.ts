import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

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
      street: [null],
      plz: [null],
      stadt: [null],
      seats: ['', Validators.required]
    });
  }

}
