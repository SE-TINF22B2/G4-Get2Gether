import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Event} from "../../../model/event";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {MatDialog} from "@angular/material/dialog";
import {EventService} from "../../../services/event.service";
import {CreateEventDialogComponent} from "../../create-event/create-event-dialog.component";


@Component({
  selector: 'app-event-banner',
  templateUrl: './event-banner.component.html',
  styleUrl: './event-banner.component.scss'
})
export class EventBannerComponent implements OnInit {
  @Input()
  eventData!: Event;

  @Output() showParticipantsClicked: EventEmitter<any> = new EventEmitter();


  isPhonePortrait = false;

  constructor(
    private breakpointObserver: BreakpointObserver,
    private dialog: MatDialog,
    private service: EventService
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
      if(!updateCommand) return;
      this.service.updateEvent(this.eventData.id, updateCommand).subscribe((event) => {
          //TODO: Update Eventpage and navbar
        }
      );
    });
  }
}
