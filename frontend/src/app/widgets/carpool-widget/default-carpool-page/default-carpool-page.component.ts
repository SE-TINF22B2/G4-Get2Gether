import {Component, EventEmitter, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {AddCarpoolDialogComponent} from "../add-carpool-dialog/add-carpool-dialog.component";

@Component({
  selector: 'app-default-carpool-page',
  templateUrl: './default-carpool-page.component.html',
  styleUrl: './default-carpool-page.component.scss'
})
export class DefaultCarpoolPageComponent {

  constructor(
    public dialog: MatDialog
  ) {}

  @Output()
  onCreateCarpoolClicked = new EventEmitter();

  openCreateCarpoolDialog() {
    this.dialog.open(AddCarpoolDialogComponent, {maxWidth: "800px"});
  }
}
