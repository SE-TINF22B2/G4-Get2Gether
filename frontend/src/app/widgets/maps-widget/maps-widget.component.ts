import {Component, EventEmitter, Inject, Input, OnInit, Output} from '@angular/core';
import {MAP_LOADED} from "../../app.module";
import {Observable} from "rxjs";
import {Location, LocationAddCommand, MapWidget} from "../../../model/map";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {MapWidgetService} from "../../../services/widgets/map-widget.service";
import {animate, style, transition, trigger} from "@angular/animations";
import {AddLocationDialogComponent} from "./add-location-dialog/add-location-dialog.component";
import {MatDialog} from "@angular/material/dialog";

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

  @Input()
  widget!: MapWidget;

  @Output()
  onWidgetUpdated = new EventEmitter<MapWidget>();

  mapOptions: google.maps.MapOptions = {
    fullscreenControl: false,
    streetViewControl: false,
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
    this.breakpointObserver.observe([Breakpoints.XSmall, Breakpoints.Small]).subscribe(result => {
      this.isSmallLayout = result.matches;
    });
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
        .subscribe(updatedWidget => this.onWidgetUpdated.emit(updatedWidget));
    }
  }

  focusLocation(location: Omit<Location, "id">) {
    this.mapOptions = {
      ...this.mapOptions,
      zoom: 17,
      center: this.locationToLatLngLiteral(location)
    };
  }

  onDeleteClicked(event: Event, location: Location) {
    event.stopPropagation();
    this.service.deleteLocation(this.eventId, this.widget.id, location.id)
      .subscribe(updatedWidget => this.onWidgetUpdated.emit(updatedWidget));
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
