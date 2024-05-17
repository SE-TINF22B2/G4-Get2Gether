import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BaseWidget} from "../../../model/common-widget";
import {Event} from "../../../model/event";
import {ExpenseEntry, ExpenseSplitWidget} from "../../../model/expense-split-widget";
import {MatDialog} from "@angular/material/dialog";
import {
  CreateEditExpenseEntryDialogComponent
} from "./create-edit-expense-entry-dialog/create-edit-expense-entry-dialog.component";
import {ExpenseSplitWidgetService} from "../../../services/widgets/expense-split-widget.service";

@Component({
  selector: 'app-expense-split-widget',
  templateUrl: './expense-split-widget.component.html',
  styleUrl: './expense-split-widget.component.scss'
})
export class ExpenseSplitWidgetComponent {

  @Input()
  eventData!: Event;

  @Input({transform: (value: BaseWidget): ExpenseSplitWidget => value as ExpenseSplitWidget})
  widget!: ExpenseSplitWidget;

  @Output()
  onWidgetUpdated = new EventEmitter<ExpenseSplitWidget>();

  constructor(
    private service: ExpenseSplitWidgetService,
    private dialog: MatDialog
  ) {
  }

  createNewExpenseEntry() {
    const dialogRef = this.dialog.open(CreateEditExpenseEntryDialogComponent, {
      width: "600px",
      data: {users: this.eventData.participants}
    });

    dialogRef.afterClosed().subscribe(addCommand => {
      if (!addCommand) return;

      this.service.createExpenseEntry(this.eventData.id, this.widget.id, addCommand)
        .subscribe(widget => this.onWidgetUpdated.emit(widget));
    })
  }

  editExpenseEntry(entry: ExpenseEntry) {
    const dialogRef = this.dialog.open(CreateEditExpenseEntryDialogComponent, {
      width: "600px",
      data: {
        users: this.eventData.participants,
        entry: entry
      }
    });

    dialogRef.afterClosed().subscribe(updateCommand => {
      if (!updateCommand) return;

      this.service.updateExpenseEntry(this.eventData.id, this.widget.id, entry.id, updateCommand)
        .subscribe(widget => this.onWidgetUpdated.emit(widget));
    })
  }

  deleteExpenseEntry(entry: ExpenseEntry) {
    this.service.deleteExpenseEntry(this.eventData.id, this.widget.id, entry.id)
      .subscribe(widget => this.onWidgetUpdated.emit(widget));
  }

}
