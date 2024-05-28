import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {AddCarpoolDialogComponent} from "./add-carpool-dialog/add-carpool-dialog.component";
import {Event} from "../../../model/event";
import {BaseWidget} from "../../../model/common-widget";
import {CarpoolWidget} from "../../../model/carpool-widget";
import {CarpoolWidgetService} from "../../../services/widgets/carpool-widget.service";
import {FehlerhandlingComponent} from "../../fehlerhandling/fehlerhandling.component";
import {MatSnackBar} from "@angular/material/snack-bar";

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

  private showMessage(messageToShow: string) {
    this._snackbar.open(messageToShow, 'schließen', {
      duration: 5000,
      panelClass: "successfull"
    });
  }

}
