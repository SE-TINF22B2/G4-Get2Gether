import { Component } from '@angular/core';
import {AddLocationDialogComponent} from "../../maps-widget/add-location-dialog/add-location-dialog.component";
import {AddAuftragDialogComponent} from "../add-auftrag-dialog/add-auftrag-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-default-page',
  templateUrl: './default-page.component.html',
  styleUrl: './default-page.component.scss'
})
export class DefaultPageComponent {
  constructor(private dialog: MatDialog) {
  }
  openAddShoppingDialog() {
    const dialogRef = this.dialog.open(AddAuftragDialogComponent, {width: "400px"});
  }
}
