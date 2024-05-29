import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {AddCarpoolDialogComponent} from "./add-carpool-dialog/add-carpool-dialog.component";
import {Event} from "../../../model/event";
import {BaseWidget} from "../../../model/common-widget";
import {Car, CarpoolWidget} from "../../../model/carpool-widget";
import {CarpoolWidgetService} from "../../../services/widgets/carpool-widget.service";
import {FehlerhandlingComponent} from "../../fehlerhandling/fehlerhandling.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {
  CreateEditExpenseEntryDialogComponent
} from "../expense-split-widget/create-edit-expense-entry-dialog/create-edit-expense-entry-dialog.component";
import {
  DeleteCarConfirmationDialogComponent
} from "./delete-car-confirmation-dialog/delete-car-confirmation-dialog.component";

@Component({
  selector: 'app-carpool-widget',
  templateUrl: './carpool-widget.component.html',
  styleUrl: './carpool-widget.component.scss'
})
export class CarpoolWidgetComponent {
  @Input()
  eventData!: Event;

  @Input({transform: (value: BaseWidget): CarpoolWidget => value as CarpoolWidget})
  widget!: CarpoolWidget;

  @Output()
  onWidgetUpdated = new EventEmitter<CarpoolWidget>();

  constructor(
    private dialog: MatDialog,
    private service: CarpoolWidgetService,
    private _snackbar: MatSnackBar) {
  }

  createNewCar() {
    const dialogRef = this.dialog.open(AddCarpoolDialogComponent, {
      width: "800px",
      data: {}
    });

    dialogRef.afterClosed().subscribe(addCommand => {
      if (!addCommand) return;

      this.service.addCar(this.eventData.id, this.widget.id, addCommand).subscribe({
        next: widget => {
          this.onWidgetUpdated.emit(widget);
          this.showMessage("Fahrgemeinschaft erstellt");
        },
        error: err => {
          this.dialog.open(FehlerhandlingComponent, {data: {error: err}})
        }
      });
    })
  }

  editCar(car: Car) {
    const dialogRef = this.dialog.open(AddCarpoolDialogComponent, {
      width: "800px",
      data: {
        car: car
      }
    });

    dialogRef.afterClosed().subscribe(updateCommand => {
      if (!updateCommand) return;

      this.service.editCar(this.eventData.id, this.widget.id, car.id, updateCommand)
        .subscribe({
            next: widget => {
              this.onWidgetUpdated.emit(widget);
              this.showMessage("Fahrgemeinschaft bearbeitet");
            },
            error: err => {
              this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
            }
          });
    })
  }

  deleteCar(car: Car) {
    const dialogRef = this.dialog.open(DeleteCarConfirmationDialogComponent);

    dialogRef.afterClosed().subscribe(deleteCar =>{
      if(!deleteCar) return;

      this.service.deleteCar(this.eventData.id, this.widget.id, car.id)
        .subscribe({
          next: widget => {
            this.onWidgetUpdated.emit(widget);
            this.showMessage("Fahrgemeinschaft gelöscht");
          },
          error: err => {
            this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
          }
        });
    })
  }

  private showMessage(messageToShow: string) {
    this._snackbar.open(messageToShow, 'schließen', {
      duration: 5000,
      panelClass: "successfull"
    });
  }
}
