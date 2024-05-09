import {Component, ElementRef, Input} from '@angular/core';
import {BaseWidget} from "../../../../model/common-widget";

@Component({
  selector: 'app-widget-container',
  templateUrl: './widget-container.component.html',
  styleUrl: './widget-container.component.scss'
})
export class WidgetContainerComponent {

  @Input()
  widget!: BaseWidget;

  constructor(public elementRef: ElementRef) {
  }

}
