import {Component, Input, OnInit} from '@angular/core';
import {Event} from "../../../model/event";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";

@Component({
  selector: 'app-event-description',
  templateUrl: './event-description.component.html',
  styleUrl: './event-description.component.scss'
})
export class EventDescriptionComponent implements OnInit {
  @Input() eventData: Event | undefined;

  isPhonePortrait = false;

  constructor(private breakpointObserver: BreakpointObserver) {
  }

  ngOnInit() {
    this.breakpointObserver.observe(Breakpoints.HandsetPortrait)
      .subscribe(result => {
        this.isPhonePortrait = result.matches;
      });
  }
}
