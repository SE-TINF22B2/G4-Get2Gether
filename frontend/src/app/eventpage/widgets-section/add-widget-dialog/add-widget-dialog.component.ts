import {Component, Inject, Input, OnInit} from '@angular/core';
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {Event} from "../../../../model/event";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {MapWidget} from "../../../../model/map-widget";
import {environment} from "../../../../environment/environment";
import {HttpClient} from "@angular/common/http";

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

  constructor(@Inject(MAT_DIALOG_DATA) public data: { eventData: Event }, private breakpointObserver: BreakpointObserver, private http: HttpClient) {
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

  eventId = this.data.eventData.id;
  openShoppingListWidget() {
    console.log('open Shopping List widget');
    return this.http.post<MapWidget>(`${environment.api}/event/${this.eventId}/widgets/shopping-list/`, {withCredentials: true});

  }

  openExpensesWidget() {
    console.log('open Expenses widget');
  }

  openCarpoolWidget() {
    console.log('open carpool widget');
  }

  openMapWidget() {
    console.log('open Map widget');
    return this.http.post<MapWidget>(`${environment.api}/event/${this.eventId}/widgets/map/`, {withCredentials: true});

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
    if(this.isWidgetTypePresent({widgetType: 'EXPENSES_SPLIT'})){
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

