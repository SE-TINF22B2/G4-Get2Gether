import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Event} from "../../../model/event";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {MatDialog} from "@angular/material/dialog";
import {EventService} from "../../../services/event.service";
import {CreateEventDialogComponent} from "../../create-event/create-event-dialog.component";
import {InvitationDialogComponent} from "../invitation-dialog/invitation-dialog.component";
import {Router} from "@angular/router";
import {FehlerhandlingComponent} from "../../fehlerhandling/fehlerhandling.component";


@Component({
  selector: 'app-event-banner',
  templateUrl: './event-banner.component.html',
  styleUrl: './event-banner.component.scss'
})
export class EventBannerComponent implements OnInit {
  @Input()
  eventData!: Event;

  @Output()
  onEventUpdated = new EventEmitter<Event>();

  @Output()
  onEventLeft = new EventEmitter();

  @Output()
  showParticipantsClicked = new EventEmitter();

  isPhonePortrait = false;

  constructor(
    private breakpointObserver: BreakpointObserver,
    private dialog: MatDialog,
    private service: EventService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.breakpointObserver.observe(Breakpoints.HandsetPortrait)
      .subscribe(result => {
        this.isPhonePortrait = result.matches;
      });
  }

  openEditEventDialog() {
    const dialogRef = this.dialog.open(CreateEventDialogComponent, {
      maxWidth: "800px",
      data: {event: this.eventData}
    });

    dialogRef.afterClosed().subscribe(updateCommand => {
      if (!updateCommand) return;
      this.service.updateEvent(this.eventData.id, updateCommand).subscribe({
          next: event => {
            this.onEventUpdated.emit(event);
          },
          error: err => {
            this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
          }
        });
    });
  }

  openInvitationDialog() {
    const dialogRef = this.dialog.open(InvitationDialogComponent, {
      width: "500px",
      data: {eventData: this.eventData}
    });
    const subscription = dialogRef.componentInstance.onEventUpdated.subscribe(event => {
      this.onEventUpdated.emit(event);
    });
    dialogRef.afterClosed().subscribe(() => subscription.unsubscribe());
  }

  leaveEvent() {
    this.service.leaveEvent(this.eventData.id).subscribe({
      next: response => {
      this.router.navigateByUrl("/");
      this.onEventLeft.emit();
      },
      error: err => {
        this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
      }
    });
  }

  hasValidLocation(): boolean {
    const location = this.eventData.location;
    return !!location && (!!location.street || !!location.postalCode || !!location.city);
  }

  get formattedLocation(): string {
    const location = this.eventData.location;
    if (!location) return "";
    return [location.street, location.postalCode, location.city].join(" ");
  }
}
