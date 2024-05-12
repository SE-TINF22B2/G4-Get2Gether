import { Component } from '@angular/core';
import {AddLocationDialogComponent} from "../../maps-widget/add-location-dialog/add-location-dialog.component";
import {AddAuftragDialogComponent} from "../add-auftrag-dialog/add-auftrag-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {EinkaufslisteWidgetComponent} from "../einkaufsliste-widget.component";
import {EntryAddCommand} from "../../../../model/shoppinglist-widget";

@Component({
  selector: 'app-default-shopping-page',
  templateUrl: './default-shopping-page.component.html',
  styleUrl: './default-shopping-page.component.scss'
})
export class DefaultShoppingPageComponent {
  constructor(private dialog: MatDialog, private root:EinkaufslisteWidgetComponent) {
  }
  openAddShoppingDialog() {
    const dialogRef = this.dialog.open(AddAuftragDialogComponent, {width: "400px"});
    dialogRef.afterClosed().subscribe(addCommand => {
      if (addCommand) {
        this.addAuftrag(addCommand);
      }
    });
  }
  addAuftrag(addCommand:EntryAddCommand){
    this.root.addAuftrag(addCommand);
  }
}
