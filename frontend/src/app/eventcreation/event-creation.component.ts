import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EventService} from "../../services/event.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-event-creation',
  templateUrl: './event-creation.component.html',
  styleUrl: './event-creation.component.scss'
})
export class EventCreationComponent implements OnInit{
  isEndDateTimeDisabled!:boolean;
  form!: FormGroup;

  constructor(private router: Router, private service: EventService, private fb: FormBuilder) {
  }
  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', Validators.required],
      date: [null],
      endDate: [null],
      time: [null],
      street: [null],
      plz: [null],
      stadt: [null],
      location: [null],
      description: [null]
    });
    this.isEndDateTimeDisabled = true;
  }

  createEvent():void {
    if (this.form.valid) {
      this.updateAddress();
      this.updateStartDate();
      this.service.createEvent(this.form).subscribe({
        next: response => {
          this.router.navigate(['/dashboard', response.id]);
        },
        error: error => {
          console.error('Error:', error);
        }
      });
    }
  }

  updateAddress() {
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

  updateStartDate() {
    const dateValue = this.form.get('date')?.value;
    const time = this.form.get('time')?.value;

    if (dateValue !== null && time !== null) {
      const date: Date = new Date(dateValue);
      const [hours, minutes] = time.split(":");
      date.setHours(parseInt(hours, 10), parseInt(minutes, 10), 0, 0);
      this.form.patchValue({
        date: date
      });
    }

    this.form.patchValue({
      date: dateValue
    });
  }

}
