import {Component, EventEmitter, Input, Output} from '@angular/core';
import {BaseWidget} from "../../../../model/common-widget";

@Component({
  selector: 'app-widget-label',
  templateUrl: './widget-label.component.html',
  styleUrl: './widget-label.component.scss'
})
export class WidgetLabelComponent {

  @Input()
  widget!: BaseWidget;

  @Input()
  isSelected = false;

  @Output()
  onClick = new EventEmitter();

}
