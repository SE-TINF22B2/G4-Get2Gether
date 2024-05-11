import { Component } from '@angular/core';

@Component({
  selector: 'app-add-widget-dialog',
  templateUrl: './add-widget-dialog.component.html',
  styleUrl: './add-widget-dialog.component.scss'
})
export class AddWidgetDialogComponent {
  openShoppingListWidget(){
    console.log('open Shopping List widget');
  }
  openExpensesWidget(){
    console.log('open Expenses widget');
  }
  openCarpoolWidget(){
    console.log('open carpool widget');
  }
  openMapWidget(){
    console.log('open Map widget');
  }

}
