import {Component, ElementRef, EventEmitter, Input, Output} from '@angular/core';
import {BaseWidget, WidgetType} from "../../../../model/common-widget";
import {Event} from "../../../../model/event";

@Component({
  selector: 'app-widget-container',
  templateUrl: './widget-container.component.html',
  styleUrl: './widget-container.component.scss'
})
export class WidgetContainerComponent {

  @Input()
  widget!: BaseWidget;

  @Input()
  eventData!: Event;

  @Output()
  onWidgetUpdated = new EventEmitter<BaseWidget>();

  constructor(public elementRef: ElementRef) {
  }

  protected readonly WidgetType = WidgetType;
}
