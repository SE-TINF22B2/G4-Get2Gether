import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {Event} from "../../../../model/event";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MapWidgetService} from "../../../../services/widgets/map-widget.service";
import {EinkaufslisteWidgetService} from "../../../../services/widgets/einkaufsliste-widget.service";
import {ExpenseSplitWidgetService} from "../../../../services/widgets/expense-split-widget.service";

@Component({
  selector: 'app-add-widget-dialog',
  templateUrl: './add-widget-dialog.component.html',
  styleUrl: './add-widget-dialog.component.scss'
})
export class AddWidgetDialogComponent implements OnInit {
  @Input()
  eventData!: Event;

  isPhonePortrait = false;

  isShoppingListPresent: boolean = false;
  isExpensesListPresent: boolean = false;
  isCarpoolPresent: boolean = false;
  isMAPPresent: boolean = false;
  eventId = this.data.eventData.id;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: { eventData: Event },
    private breakpointObserver: BreakpointObserver,
    private dialogRef: MatDialogRef<AddWidgetDialogComponent>,
    private mapService: MapWidgetService,
    private shoppingService: EinkaufslisteWidgetService,
    private expenseService: ExpenseSplitWidgetService) {
  }

  ngOnInit() {
    this.breakpointObserver.observe(Breakpoints.HandsetPortrait)
      .subscribe(result => {
        this.isPhonePortrait = result.matches;
      });
    this.disableShoppingList();
    this.disableExpensesList();
    this.disableCarpool();
    this.disableMap();
  }

  openShoppingListWidget() {
    this.shoppingService.addShoppingWidget(this.eventId).subscribe({
      next: response => {
        this.dialogRef.close(this.eventId);
      },
      error: error => {
        console.error('Error:', error);
      }
    });
  }

  openExpensesWidget() {
    this.expenseService.createExpenseWidget(this.eventId).subscribe({
      next: response => {
        this.dialogRef.close(this.eventId);
      },
      error: error => {
        console.error('Error:', error);
      }
    });
  }

  openCarpoolWidget() {
    console.log('open carpool widget');
    //TODO: url anpassen
    //return this.http.post<MapWidget>(`${environment.api}/event/${this.eventId}/widgets/`, {withCredentials: true});
  }

  openMapWidget() {
    this.mapService.addMapWidget(this.eventId).subscribe({
      next: response => {
        this.dialogRef.close(this.eventId);
      },
      error: error => {
        console.error('Error:', error);
      }
    });
  }

  isWidgetTypePresent({widgetType}: { widgetType: any }): boolean {
    return this.data.eventData.widgets.some(widget => widget.widgetType === widgetType);
  }

  disableShoppingList(){
    if(this.isWidgetTypePresent({widgetType: 'SHOPPING_LIST'})){
      this.isShoppingListPresent = true;
    }
  }
  disableExpensesList(){
    if(this.isWidgetTypePresent({widgetType: 'EXPENSE_SPLIT'})){
      this.isExpensesListPresent = true;
    }
  }
  disableCarpool(){
    if(this.isWidgetTypePresent({widgetType: 'CARPOOL'})){
      this.isCarpoolPresent = true;
    }
  }
  disableMap(){
    if(this.isWidgetTypePresent({widgetType: 'MAP'})){
      this.isMAPPresent = true;
    }
  }
}

