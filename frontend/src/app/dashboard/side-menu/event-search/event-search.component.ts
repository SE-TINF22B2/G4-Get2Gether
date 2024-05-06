import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-event-search',
  templateUrl: './event-search.component.html',
  styleUrl: './event-search.component.scss'
})
export class EventSearchComponent {

  @Output()
  onSearch = new EventEmitter<string>();
  value: string | undefined;

  onInputEvent(event: Event) {
    this.onSearch.emit((event.target as HTMLInputElement).value);
  }

  onClear() {
    this.value = "";
    this.onSearch.emit("");
  }
}
