import {Component, OnInit} from '@angular/core';
import {EventOverview} from "../../../model/event";
import {EventService} from "../../../services/event.service";

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrl: './side-menu.component.scss'
})
export class SideMenuComponent implements OnInit {

  events: EventOverview[] = [];

  private searchQuery = "";

  constructor(private eventService: EventService) {
  }

  ngOnInit() {
    this.eventService.getOwnEvents().subscribe({
      next: event => this.events = event
    });
  }

  onSearch(query: string) {
    this.searchQuery = query;
  }

  get filteredEvents(): EventOverview[] {
    if (!this.searchQuery.trim())
      return this.events;
    return this.events.filter(event => event.name.toLowerCase().includes(this.searchQuery.toLowerCase()));
  }

}
