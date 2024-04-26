import {Component, ElementRef, EventEmitter, Inject, Input, NgZone, OnInit, Output, ViewChild} from '@angular/core';
import {MAP_LOADED} from "../../app.module";
import {Observable} from "rxjs";
import {Location, LocationAddCommand, MapWidget} from "../../../model/map";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {MapWidgetService} from "../../../services/widgets/map-widget.service";
import {animate, style, transition, trigger} from "@angular/animations";

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
    streetViewControl: false
  }

  private autocomplete: google.maps.places.Autocomplete | undefined;

  focussedLocation: google.maps.LatLngLiteral = {
    lat: 49.02632,
    lng: 8.38544
  };
  mapZoom = 12;

  isSmallLayout = false;

  constructor(
    private service: MapWidgetService,
    @Inject(MAP_LOADED) public gmapsLoaded: Observable<boolean>,
    private ngZone: NgZone,
    private breakpointObserver: BreakpointObserver
  ) {
  }

  ngOnInit() {
    this.breakpointObserver.observe([Breakpoints.XSmall, Breakpoints.Small]).subscribe(result => {
      this.isSmallLayout = result.matches;
    });
  }

  @ViewChild("searchField", {static: false})
  set searchField(inputField: ElementRef<HTMLInputElement>) {
    if (!inputField) return;

    this.autocomplete = new google.maps.places.Autocomplete(inputField.nativeElement);
    this.autocomplete.addListener("place_changed", () => {
      const place = this.autocomplete?.getPlace();
      if (!place || !place.place_id) return;

      this.ngZone.run(() => {
        this.addLocation({
          placeId: place.place_id || "",
          name: place.name || "",
          address: place.formatted_address || "",
          latitude: place.geometry?.location?.lat() || -1,
          longitude: place.geometry?.location?.lng() || -1
        });
        inputField.nativeElement.value = "";
      });
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
    this.focussedLocation = this.locationToLatLngLiteral(location);
    this.mapZoom = 17;
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
