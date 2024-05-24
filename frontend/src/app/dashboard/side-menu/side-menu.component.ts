import {Component, OnDestroy, OnInit} from '@angular/core';
import {EventOverview} from "../../../model/event";
import {EventService} from "../../../services/event.service";
import {AppStateService} from "../../../services/app-state.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrl: './side-menu.component.scss'
})
export class SideMenuComponent implements OnInit, OnDestroy {

  events: EventOverview[] = [];

  private searchQuery = "";
  private updateEventListSubscription?: Subscription;

  constructor(private eventService: EventService, private appState: AppStateService) {
  }

  ngOnInit() {
    this.updateEventListSubscription = this.appState.doUpdateEventList.subscribe(() => {
      this.fetchOwnEvents();
    });
    this.fetchOwnEvents();
  }

  ngOnDestroy() {
    this.updateEventListSubscription?.unsubscribe();
  }

  onSearch(query: string) {
    this.searchQuery = query;
  }

  get filteredEvents(): EventOverview[] {
    if (!this.searchQuery.trim())
      return this.events;
    return this.events.filter(event => event.name.toLowerCase().includes(this.searchQuery.toLowerCase()));
  }

  private fetchOwnEvents() {
    this.eventService.getOwnEvents().subscribe({
      next: event => this.events = event
    });
  }
}
