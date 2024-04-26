import {Component, ElementRef, NgZone, ViewChild} from '@angular/core';
import {LocationAddCommand} from "../../../../model/map";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-location-dialog',
  templateUrl: './add-location-dialog.component.html',
  styleUrl: './add-location-dialog.component.scss'
})
export class AddLocationDialogComponent {

  @ViewChild("addButton", {read: ElementRef})
  private addButton!: ElementRef<HTMLButtonElement>;

  private autocomplete: google.maps.places.Autocomplete | undefined;
  private selectedLocation: google.maps.places.PlaceResult | undefined;

  constructor(private ngZone: NgZone, private dialogRef: MatDialogRef<AddLocationDialogComponent>) {
  }

  @ViewChild("searchField", {static: false})
  set searchField(inputField: ElementRef<HTMLInputElement>) {
    if (!inputField) return;

    this.autocomplete = new google.maps.places.Autocomplete(inputField.nativeElement);
    this.autocomplete.addListener("place_changed", () => {
      this.ngZone.run(() => {
        this.selectedLocation = this.autocomplete?.getPlace();
        setTimeout(() => this.addButton.nativeElement.focus());
      });
    });
  }

  closeDialog() {
    const place = this.selectedLocation;
    if (!place || !place.place_id) return;

    const addCommand: LocationAddCommand = {
      placeId: place.place_id || "",
      name: place.name || "",
      address: place.formatted_address || "",
      latitude: place.geometry?.location?.lat() || -1,
      longitude: place.geometry?.location?.lng() || -1
    };

    this.dialogRef.close(addCommand);
  }

  get isSelectedLocationInvalid(): boolean {
    return !this.selectedLocation || !this.selectedLocation.place_id;
  }

}
