import {
  Component,
  EventEmitter,
  Input,
  NgZone,
  OnDestroy,
  OnInit, Output,
  QueryList,
  ViewChildren
} from '@angular/core';
import {Event} from "../../../model/event";
import {CdkScrollable, ScrollDispatcher} from "@angular/cdk/overlay";
import {Subscription} from "rxjs";
import {WidgetContainerComponent} from "./widget-container/widget-container.component";
import {BaseWidget} from "../../../model/common-widget";
import {AddWidgetDialogComponent} from "./add-widget-dialog/add-widget-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-widgets-section',
  templateUrl: './widgets-section.component.html',
  styleUrl: './widgets-section.component.scss'
})
export class WidgetsSectionComponent implements OnInit, OnDestroy {

  @Input()
  eventData!: Event;

  @Output()
  onWidgetUpdated = new EventEmitter<BaseWidget>();

  @ViewChildren(WidgetContainerComponent)
  widgetContainers!: QueryList<WidgetContainerComponent>;

  private scrolledWidgetId: string | undefined;
  private scrollSubscription!: Subscription;

  constructor(
    private scroll: ScrollDispatcher,
    private ngZone: NgZone,
    public dialog: MatDialog
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

  scrollToWidget(widgetId: string) {
    const widgetContainer = this.widgetContainers.find(container => container.widget.id === widgetId);

    if (widgetContainer) {
      (widgetContainer.elementRef.nativeElement as HTMLElement).scrollIntoView({behavior: "smooth"});
    }
  }

  get selectedWidgetId(): string | undefined {
    if (this.scrolledWidgetId && this.eventData.widgets.some(w => w.id === this.scrolledWidgetId))
      return this.scrolledWidgetId;
    if (this.eventData.widgets.length > 0)
      return this.eventData.widgets[0].id;
    return undefined;
  }

  private onScroll(data: CdkScrollable) {
    const scrollTop = data.measureScrollOffset("top");
    // trigger when widget is scrolled to the first third of the page height
    const topOffset = window.innerHeight / 3;

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

  openDialog() {
    this.dialog.open(AddWidgetDialogComponent, {
      data: { eventData: this.eventData},
      width: '800px'
    });
  }
}
