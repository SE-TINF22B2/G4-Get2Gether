import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-default-carpool-page',
  templateUrl: './default-carpool-page.component.html',
  styleUrl: './default-carpool-page.component.scss'
})
export class DefaultCarpoolPageComponent {
  @Output()
  onCreateCarpoolClicked = new EventEmitter();

}
