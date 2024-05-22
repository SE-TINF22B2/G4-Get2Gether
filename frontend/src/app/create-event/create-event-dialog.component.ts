import {Component, Inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CreateEventCommand, Event} from "../../model/event";

@Component({
  selector: 'app-event-creation',
  templateUrl: './create-event-dialog.component.html',
  styleUrl: './create-event-dialog.component.scss'
})
export class CreateEventDialogComponent {
  isEndDateTimeDisabled!: boolean;
  event: Event | undefined;
  form!: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) data: {event: Event | undefined},
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<CreateEventDialogComponent>,
  ) {
    this.event = data.event;
    const location = this.extractAddress(this.event?.location);
    const time = this.extractDateAndTime(this.event?.date);
    this.form = this.fb.group({
      name: [this.event?.name ?? "", Validators.required],
      date: [this.event?.date ?? null],
      endDate: [this.event?.endDate ?? null],
      time: [time ?? null],
      street: [location?.street ?? null],
      plz: [location?.postalCode ?? null],
      stadt: [location?.city ?? null],
      location: [this.event?.location ?? null],
      description: [this.event?.description ?? ""]
    });
    this.isEndDateTimeDisabled = true;
  }

  private extractDateAndTime(date: string | undefined) {
    if(date) {
      const result = new Date(date);
      const hours = result.getHours().toString().padStart(2,'0');
      const minutes = result.getMinutes().toString().padStart(2,'0');
      const seconds = result.getSeconds().toString().padStart(2,'0');
      return `${hours}:${minutes}:${seconds}`;
    }
    return null;
  }

  private extractAddress(location: string | undefined) {
    if(location) {
      const addressRegex = /^(?<street>[\w\säöüÄÖÜß]+ \d+) (?<postalCode>\d{5}) (?<city>[\w\säöüÄÖÜß]+)$/;
      const match = location.match(addressRegex);
      if (match && match.groups) {
        return {
          street: match.groups['street'],
          postalCode: match.groups['postalCode'],
          city: match.groups['city']
        };
      }
    }
    return null;
  }

  submit(): void {
    if (this.form.valid) {
      this.updateAddress();
      this.updateStartDate();
      this.updateEndDate();
      let data: CreateEventCommand = {
        name: this.form.value.name,
        date: this.form.value.date,
        endDate: this.form.value.endDate,
        location: this.form.value.location,
        description: this.form.value.description
      }
      this.dialogRef.close(data);
    }
  }

  private updateAddress() {
    const street = this.form.get('street')?.value || '';
    const plz = this.form.get('plz')?.value || '';
    const city = this.form.get('stadt')?.value || '';

    if (!street && !plz && !city) {
      return;
    }

    const fullAddress = `${street} ${plz} ${city}`;
    this.form.patchValue({
      location: fullAddress
    });
  }

  private updateStartDate() {
    const dateValue = this.form.get('date')?.value;
    const time = this.form.get('time')?.value;
    if (dateValue) {
      let date = new Date();
      date.setUTCDate(new Date(dateValue).getDate());
      if (time) {
        const [hours, minutes] = time.split(":");
        date.setUTCHours(parseInt(hours, 10), parseInt(minutes, 10), 0, 0);
      } else {
        date.setUTCHours(0, 0, 0, 0);
      }
      this.form.patchValue({
        date: date
      });
    }
  }

  private updateEndDate() {
    const dateValue = this.form.get('endDate')?.value;
    if (dateValue) {
      let date = new Date();
      date.setUTCDate(new Date(dateValue).getDate());
      date.setUTCHours(23, 59, 0, 0);
      this.form.patchValue({
        endDate: date
      });
    }
  }

  get isCreatingNewEvent(): boolean {
    return !this.event;
  }

}
