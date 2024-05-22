import {Component, OnDestroy, OnInit} from '@angular/core';
import {EventService} from "../../services/event.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {Event} from "../../model/event";
import {BaseWidget} from "../../model/common-widget";

@Component({
  selector: 'app-eventpage',
  templateUrl: './eventpage.component.html',
  styleUrl: './eventpage.component.scss'
})
export class EventpageComponent implements OnInit, OnDestroy {

  private routeSubscription!: Subscription;

  eventData: Event | undefined;

  constructor(
    private eventService: EventService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.routeSubscription = this.route.params.subscribe(params => {
      const id = params['id'];
      if (!id || (this.eventData && this.eventData.id === id)) {
        // do nothing if ID is not defined, or it's the same as the currently displayed event
        return;
      }
      this.loadEventData(id);
    });
  }

  ngOnDestroy() {
    this.routeSubscription.unsubscribe();
  }

  onWidgetUpdated(widget: BaseWidget) {
    if (!this.eventData) return;

    const index = this.eventData.widgets.findIndex(w => w.id === widget.id);
    if (index === -1) {
      console.log("ERROR: Cannot update widget: Widget not found.", widget);
      return;
    }
    this.eventData.widgets[index] = widget;
  }

  onEventUpdated(event: Event) {
    this.eventData = event;
  }

  private loadEventData(eventId: string) {
    this.eventService.getSingleEvent(eventId).subscribe({
      next: data => this.eventData = data,
      error: error => {
        console.log("Failed to load event data.", error);
        this.router.navigate([".."]);
      }
    });
  }

}
