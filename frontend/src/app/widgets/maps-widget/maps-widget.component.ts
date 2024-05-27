import {Component, EventEmitter, Inject, Input, OnInit, Output, ViewChild} from '@angular/core';
import {MAP_LOADED} from "../../app.module";
import {Observable} from "rxjs";
import {Location, LocationAddCommand, MapWidget} from "../../../model/map-widget";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {MapWidgetService} from "../../../services/widgets/map-widget.service";
import {animate, style, transition, trigger} from "@angular/animations";
import {AddLocationDialogComponent} from "./add-location-dialog/add-location-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {MatDrawer} from "@angular/material/sidenav";
import {BaseWidget} from "../../../model/common-widget";
import {GoogleMap} from "@angular/google-maps";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {FehlerhandlingComponent} from "../../fehlerhandling/fehlerhandling.component";

@Component({
  selector: 'app-maps-widget',
  templateUrl: './maps-widget.component.html',
  styleUrl: './maps-widget.component.scss',
  animations: [
    trigger("slideOut", [
      transition(":leave", [
        style({opacity: 1, transform: "translateX(0%)"}),
        animate("0.3s ease-out", style({opacity: 0, transform: "translateX(100%)"}))
      ])
    ])
  ]
})
export class MapsWidgetComponent implements OnInit {

  @Input()
  eventId!: string;

  private _widget!: MapWidget;

  @Output()
  onWidgetUpdated = new EventEmitter<MapWidget>();

  @ViewChild("drawer")
  private drawer!: MatDrawer;

  @ViewChild("googleMap")
  private googleMap!: GoogleMap;

  mapOptions: google.maps.MapOptions = {
    fullscreenControl: false,
    streetViewControl: false,
    mapTypeId: "roadmap",
    zoom: 12,
    center: {
      lat: 49.02632,
      lng: 8.38544
    }
  };

  isSmallLayout = false;

  constructor(
    private service: MapWidgetService,
    private dialog: MatDialog,
    @Inject(MAP_LOADED) public gmapsLoaded: Observable<boolean>,
    private breakpointObserver: BreakpointObserver
  ) {
  }

  ngOnInit() {
    this.breakpointObserver.observe([Breakpoints.XSmall, Breakpoints.Small, Breakpoints.Medium]).subscribe(result => {
      this.isSmallLayout = result.matches;
    });
  }

  @Input({transform: (value: BaseWidget): MapWidget => value as MapWidget})
  set widget(value: MapWidget) {
    const hasChanged = value !== this._widget;
    this._widget = value;
    if (hasChanged && value.locations.length > 0) {
      this.focusLocation(value.locations[0]);
    }
  }

  get widget(): MapWidget {
    return this._widget;
  }

  openAddLocationDialog() {
    const dialogRef = this.dialog.open(AddLocationDialogComponent, {width: "400px"});
    dialogRef.afterClosed().subscribe(addCommand => {
      if (addCommand) {
        this.addLocation(addCommand);
      }
    });
  }

  private addLocation(addCommand: LocationAddCommand) {
    this.focusLocation(addCommand);

    const existingLocation = this.widget.locations.find(location => location.placeId === addCommand.placeId);

    if (!existingLocation) {
      this.service.addLocation(this.eventId, this.widget.id, addCommand)
        .subscribe({
          next: updatedWidget => {
            this.onWidgetUpdated.emit(updatedWidget);
          },
          error: err => {
            this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
          }
        });
    }
  }

  focusLocation(location: Omit<Location, "id">) {
    this.mapOptions = {
      ...this.mapOptions,
      zoom: 17,
      center: this.locationToLatLngLiteral(location),
      mapTypeId: this.googleMap ? this.googleMap.getMapTypeId() : this.mapOptions.mapTypeId
    };

    if (this.isSmallLayout) {
      this.drawer.close();
    }
  }

  onDeleteClicked(event: Event, location: Location) {
    event.stopPropagation();
    this.service.deleteLocation(this.eventId, this.widget.id, location.id)
      .subscribe({
        next: updatedWidget => {
          this.onWidgetUpdated.emit(updatedWidget);
        },
        error: err => {
          this.dialog.open(FehlerhandlingComponent, {data: {error: err}});
        }
      });
  }

  get locations(): Location[] {
    return this.widget.locations;
  }

  get mapsMarkerPositions(): google.maps.LatLngLiteral[] {
    return this.widget.locations.map(this.locationToLatLngLiteral);
  }

  private locationToLatLngLiteral(location: Omit<Location, "id">): google.maps.LatLngLiteral {
    return {
      lng: location.longitude,
      lat: location.latitude,
    };
  }

}
