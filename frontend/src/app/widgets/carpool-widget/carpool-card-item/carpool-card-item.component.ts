import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Event} from "../../../../model/event";
import {Car} from "../../../../model/carpool-widget";
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../model/user";
import {getUserNameForParticipant} from "../../../../utils/user.utils";

@Component({
  selector: 'app-carpool-card-item',
  templateUrl: './carpool-card-item.component.html',
  styleUrl: './carpool-card-item.component.scss'
})
export class CarpoolCardItemComponent {

  @Input()
  eventData!: Event;

  @Input()
  car!: Car;

  @Output()
  onDelete = new EventEmitter();

  @Output()
  onEdit = new EventEmitter();

  @Output()
  onEditRider = new EventEmitter();

  @Output()
  onRiderDelete = new EventEmitter();

  isExpanded: boolean = false;

  constructor(public userService: UserService) {
  }

  get getRiders(): string[] {
    return this.car.riders.map(u => getUserNameForParticipant(u.user));
  }

  get isCarFull(): boolean {
    return this.car.riders.length >= this.car.anzahlPlaetze;
  }

  isUserDriver(user: User) {
    return this.car.driver.id === user.id;
  }

  isUserRider(user: User) {
    return this.car.riders.some(rider => rider.user.id === user.id);
  }

  getLocation() {
    const location = [this.eventData.location.postalCode, this.eventData.location.city].join(" ");
    return [this.eventData.location.street, location].join(", ");
  }

  protected readonly getUserNameForParticipant = getUserNameForParticipant;
}
