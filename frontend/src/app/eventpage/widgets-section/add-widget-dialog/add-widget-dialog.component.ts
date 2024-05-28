import {Component, Inject, OnInit} from '@angular/core';
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {Event} from "../../../../model/event";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {MapWidgetService} from "../../../../services/widgets/map-widget.service";
import {EinkaufslisteWidgetService} from "../../../../services/widgets/einkaufsliste-widget.service";
import {ExpenseSplitWidgetService} from "../../../../services/widgets/expense-split-widget.service";
import {WidgetType} from "../../../../model/common-widget";
import {Observable} from "rxjs";
import {FehlerhandlingComponent} from "../../../fehlerhandling/fehlerhandling.component";
import {CarpoolWidgetService} from "../../../../services/widgets/carpool-widget.service";

@Component({
  selector: 'app-add-widget-dialog',
  templateUrl: './add-widget-dialog.component.html',
  styleUrl: './add-widget-dialog.component.scss'
})
export class AddWidgetDialogComponent implements OnInit {

  private eventData: Event;

  isPhonePortrait = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) data: { eventData: Event },
    private breakpointObserver: BreakpointObserver,
    private dialogRef: MatDialogRef<AddWidgetDialogComponent>,
    public dialog: MatDialog,
    private mapService: MapWidgetService,
    private shoppingListService: EinkaufslisteWidgetService,
    private expenseSplitService: ExpenseSplitWidgetService,
    private carpoolService: CarpoolWidgetService
  ) {
    this.eventData = data.eventData;
  }

  ngOnInit() {
    this.breakpointObserver.observe(Breakpoints.HandsetPortrait)
      .subscribe(result => {
        this.isPhonePortrait = result.matches;
      });
  }

  createShoppingListWidget() {
    this.createWidget(this.shoppingListService.createShoppingWidget(this.eventId));
  }

  createExpenseSplitWidget() {
    this.createWidget(this.expenseSplitService.createExpenseWidget(this.eventId));
  }

  createCarpoolWidget() {
    this.createWidget(this.carpoolService.createCarPoolWidget(this.eventId));
  }

  createMapWidget() {
    this.createWidget(this.mapService.createMapWidget(this.eventId));
  }

  private createWidget(requestObservable: Observable<Event>) {
    requestObservable.subscribe({
      next: event => {
        this.dialogRef.close(event);
      },
      error: error => {
        this.dialog.open(FehlerhandlingComponent, {data: {error: error}});
      }
    });
  }

  get isShoppingListPresent(): boolean {
    return this.isWidgetTypePresent(WidgetType.SHOPPING_LIST);
  }

  get isExpenseSplitPresent(): boolean {
    return this.isWidgetTypePresent(WidgetType.EXPENSE_SPLIT);
  }

  get isCarpoolPresent(): boolean {
    return this.isWidgetTypePresent(WidgetType.CARPOOL);
  }

  get isMapPresent(): boolean {
    return this.isWidgetTypePresent(WidgetType.MAP);
  }

  private isWidgetTypePresent(widgetType: WidgetType): boolean {
    return this.eventData.widgets.some(widget => widget.widgetType === widgetType);
  }

  private get eventId(): string {
    return this.eventData.id;
  }
}

