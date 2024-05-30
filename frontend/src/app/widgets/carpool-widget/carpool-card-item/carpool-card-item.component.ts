import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Event} from "../../../../model/event";
import {Car} from "../../../../model/carpool-widget";
import {UserService} from "../../../../services/user.service";
import {SimpleUser, User} from "../../../../model/user";

@Component({
  selector: 'app-carpool-card-item',
  templateUrl: './carpool-card-item.component.html',
  styleUrl: './carpool-card-item.component.scss'
})
export class CarpoolCardItemComponent implements OnInit{

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

  currentUser: User | undefined;

  constructor(public userService: UserService) {
  }

  get getRiders(): string[] {
    return this.car.riders.map(u => [u.user.firstName, u.user.lastName].join(" "));
  }

  ngOnInit(): void {
    this.userService.fetchUserModel().subscribe(user => this.currentUser = user);
  }

  isUserDriver(user: User) {
    return this.car.driverId == user.id;
  }

  isGuest() {
      return !this.currentUser;
  }
}
