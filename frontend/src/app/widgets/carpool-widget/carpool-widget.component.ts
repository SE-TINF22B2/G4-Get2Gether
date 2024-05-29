import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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
import {AddRiderDialogComponent} from "./add-rider-dialog/add-rider-dialog.component";
import {UserService} from "../../../services/user.service";
import {User} from "../../../model/user";

@Component({
  selector: 'app-carpool-widget',
  templateUrl: './carpool-widget.component.html',
  styleUrl: './carpool-widget.component.scss'
})
export class CarpoolWidgetComponent implements OnInit{
  @Input()
  eventData!: Event;

  @Input({transform: (value: BaseWidget): CarpoolWidget => value as CarpoolWidget})
  widget!: CarpoolWidget;

  @Output()
  onWidgetUpdated = new EventEmitter<CarpoolWidget>();

  currentUser: User | undefined;

  constructor(
    private dialog: MatDialog,
    private service: CarpoolWidgetService,
    private _snackbar: MatSnackBar,
    private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.fetchUserModel().subscribe(user => this.currentUser = user);
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

  addRider(car: Car) {
    const dialogRef = this.dialog.open(AddRiderDialogComponent);

    dialogRef.afterClosed().subscribe(addRiderCommand => {
      if(!addRiderCommand) return;

      this.service.addRider(this.eventData.id, this.widget.id, car.id, addRiderCommand)
        .subscribe({
          next: widget => {
            this.onWidgetUpdated.emit(widget);
            this.showMessage("Fahrgemeinschaft beigetreten")
          },
          error: err => {
            this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
          }
        });
    });
  }

  deleteRider(car: Car) {
    if(!this.currentUser) return;
    this.service.deleteRider(this.eventData.id, this.widget.id, car.id, this.currentUser.id)
      .subscribe(
        {
          next: widget => {
            this.onWidgetUpdated.emit(widget);
            this.showMessage("Fahrgemeinschaft verlassen")
          },
          error: err => {
            this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
          }
        });
  }

  private showMessage(messageToShow: string) {
    this._snackbar.open(messageToShow, 'schließen', {
      duration: 5000,
      panelClass: "successfull"
    });
  }
}
