import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BaseWidget} from "../../../model/common-widget";
import {Event} from "../../../model/event";
import {ExpenseEntry, ExpenseSplitWidget} from "../../../model/expense-split-widget";
import {MatDialog} from "@angular/material/dialog";
import {
  CreateEditExpenseEntryDialogComponent
} from "./create-edit-expense-entry-dialog/create-edit-expense-entry-dialog.component";
import {ExpenseSplitWidgetService} from "../../../services/widgets/expense-split-widget.service";
import {
  DeleteEntryConfirmationDialogComponent
} from "./delete-entry-confirmation-dialog/delete-entry-confirmation-dialog.component";
import {FehlerhandlingComponent} from "../../fehlerhandling/fehlerhandling.component";

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
      data: {users: this.eventData.participants.filter(p => !p.hasLeft)}
    });

    dialogRef.afterClosed().subscribe(addCommand => {
      if (!addCommand) return;

      this.service.createExpenseEntry(this.eventData.id, this.widget.id, addCommand)
        .subscribe({
          next: widget => {
            this.onWidgetUpdated.emit(widget);
          },
          error: err => {
            this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
          }
        });
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
        .subscribe({
          next: widget => {
            this.onWidgetUpdated.emit(widget)
          },
          error: err => {
            this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
          }
        });
    })
  }

  deleteExpenseEntry(entry: ExpenseEntry) {
    const dialogRef = this.dialog.open(DeleteEntryConfirmationDialogComponent);

    dialogRef.afterClosed().subscribe(deleteExpenseEntry => {
      if (!deleteExpenseEntry) return;

      this.service.deleteExpenseEntry(this.eventData.id, this.widget.id, entry.id)
        .subscribe({
          next: widget => {
            this.onWidgetUpdated.emit(widget);
          },
          error: err => {
            this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
          }
        });
    })
  }

}
