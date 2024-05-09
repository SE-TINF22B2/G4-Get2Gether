import {
  Component,
  ElementRef,
  Input,
  NgZone,
  OnDestroy,
  OnInit,
  QueryList,
  ViewChild,
  ViewChildren
} from '@angular/core';
import {Event} from "../../../model/event";
import {CdkScrollable, ScrollDispatcher} from "@angular/cdk/overlay";
import {Subscription} from "rxjs";
import {WidgetContainerComponent} from "./widget-container/widget-container.component";
import {BaseWidget} from "../../../model/common-widget";

@Component({
  selector: 'app-widgets-section',
  templateUrl: './widgets-section.component.html',
  styleUrl: './widgets-section.component.scss'
})
export class WidgetsSectionComponent implements OnInit, OnDestroy {

  @Input()
  eventData!: Event;

  @ViewChildren(WidgetContainerComponent)
  widgetContainers!: QueryList<WidgetContainerComponent>;

  @ViewChild("widgetBar")
  widgetBar!: ElementRef<HTMLElement>;

  scrolledWidgetId: string | undefined;

  private scrollSubscription!: Subscription;

  constructor(
    private scroll: ScrollDispatcher,
    private ngZone: NgZone
  ) {
  }

  ngOnInit() {
    this.scrollSubscription = this.scroll.scrolled().subscribe(data => {
      if (data instanceof CdkScrollable) {
        this.onScroll(data);
      }
    });

    // select first widget as default selected
    if (this.eventData.widgets.length > 0) {
      this.scrolledWidgetId = this.eventData.widgets[0].id;
    }
  }

  ngOnDestroy() {
    this.scrollSubscription.unsubscribe();
  }

  onWidgetUpdated(widget: BaseWidget) {
    const index = this.eventData.widgets.findIndex(w => w.id === widget.id);
    if (index === -1) {
      console.log("ERROR: Cannot update widget: Widget not found.", widget);
      return;
    }
    this.eventData.widgets[index] = widget;
  }

  scrollToWidget(widgetId: string) {
    const widgetContainer = this.widgetContainers.find(container => container.widget.id === widgetId);

    if (widgetContainer) {
      (widgetContainer.elementRef.nativeElement as HTMLElement).scrollIntoView({behavior: "smooth"});
    }
  }

  private onScroll(data: CdkScrollable) {
    const scrollTop = data.measureScrollOffset("top");
    const topOffset = this.widgetBar.nativeElement.getBoundingClientRect().bottom + 10;

    let scrolledWidgetId: string | undefined;

    this.widgetContainers.forEach(widgetContainer => {
      const widgetElement = widgetContainer.elementRef.nativeElement as HTMLElement;
      const widgetOffset = widgetElement.offsetTop;

      if ((widgetOffset - topOffset) < scrollTop) {
        scrolledWidgetId = widgetContainer.widget.id;
      }
    });

    this.ngZone.run(() => {
      if (scrolledWidgetId) {
        this.scrolledWidgetId = scrolledWidgetId;
      } else if (this.eventData.widgets.length > 0) {
        this.scrolledWidgetId = this.eventData.widgets[0].id;
      } else {
        this.scrolledWidgetId = undefined;
      }
    });
  }

}
