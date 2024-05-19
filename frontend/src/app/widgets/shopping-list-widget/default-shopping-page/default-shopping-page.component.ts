import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-default-shopping-page',
  templateUrl: './default-shopping-page.component.html',
  styleUrl: './default-shopping-page.component.scss'
})
export class DefaultShoppingPageComponent {

  @Output()
  onCreateEntryClicked = new EventEmitter();

}
