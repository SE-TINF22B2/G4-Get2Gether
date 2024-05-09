import {Component, Input, NgZone, OnDestroy, OnInit, QueryList, ViewChildren} from '@angular/core';
import {Event} from "../../../model/event";
import {CdkScrollable, ScrollDispatcher} from "@angular/cdk/overlay";
import {Subscription} from "rxjs";
import {WidgetContainerComponent} from "./widget-container/widget-container.component";

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

  scrolledWidgetId: string | undefined;

  private scrollSubscription!: Subscription;

  constructor(
    private scroll: ScrollDispatcher,
    private ngZone: NgZone
  ) {
  }

  ngOnInit() {
    if (this.eventData.widgets.length > 0) {
      this.scrolledWidgetId = this.eventData.widgets[0].id;
    }

    this.scrollSubscription = this.scroll.scrolled().subscribe(data => {
      if (data instanceof CdkScrollable) {
        this.onScroll(data);
      }
    });
  }

  ngOnDestroy() {
    this.scrollSubscription.unsubscribe();
  }

  scrollToWidget(widgetId: string) {
    const widgetContainer = this.widgetContainers.find(container => container.widget.id === widgetId);

    if (widgetContainer) {
      (widgetContainer.elementRef.nativeElement as HTMLElement).scrollIntoView({behavior: "smooth"});
    }
  }

  private onScroll(data: CdkScrollable) {
    const scrollTop = data.measureScrollOffset("top");
    const topOffset = 50;

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
