import {Component, ElementRef, Inject, NgZone, OnInit, ViewChild} from '@angular/core';
import {MAP_LOADED} from "../../app.module";
import {Observable} from "rxjs";
import {Location} from "../../../model/map";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";

@Component({
  selector: 'app-maps-widget',
  templateUrl: './maps-widget.component.html',
  styleUrl: './maps-widget.component.scss'
})
export class MapsWidgetComponent implements OnInit {

  mapOptions: google.maps.MapOptions = {
    fullscreenControl: false,
    streetViewControl: false
  }

  private autocomplete: google.maps.places.Autocomplete | undefined;

  locations: Location[] = [];
  focussedLocation: google.maps.LatLngLiteral = {
    lat: 49.02632,
    lng: 8.38544
  };
  mapZoom = 12;

  isSmallLayout = false;

  constructor(
    @Inject(MAP_LOADED) public gmapsLoaded: Observable<boolean>,
    private ngZone: NgZone,
    private breakpointObserver: BreakpointObserver
  ) {}

  ngOnInit() {
    this.breakpointObserver.observe([Breakpoints.XSmall, Breakpoints.Small]).subscribe(result => {
      this.isSmallLayout = result.matches;
    });
  }

  @ViewChild("searchField", {static: false})
  set searchField(inputField: ElementRef<HTMLInputElement>) {
    console.log("field", inputField);
    if (!inputField) return;

    this.autocomplete = new google.maps.places.Autocomplete(inputField.nativeElement);
    this.autocomplete.addListener("place_changed", () => {
      const place = this.autocomplete?.getPlace();
      if (!place || !place.place_id) return;

      this.ngZone.run(() => {
        const existingLocation = this.locations.find(location => location.id === place.place_id);

        let location: Location;
        if (existingLocation) {
          location = existingLocation;
        } else {
          location = {
            id: place.place_id || "",
            name: place.name || "",
            address: place.formatted_address || "",
            latitude: place.geometry?.location?.lat() || -1,
            longitude: place.geometry?.location?.lng() || -1
          };
          this.locations.push(location);
        }

        inputField.nativeElement.value = "";
        this.focusLocation(location);
      });
    });
  }

  focusLocation(location: Location) {
    this.focussedLocation = this.locationToLatLngLiteral(location);
    this.mapZoom = 17;
  }

  get mapsMarkerPositions(): google.maps.LatLngLiteral[] {
    return this.locations.map(this.locationToLatLngLiteral);
  }

  private locationToLatLngLiteral(location: Location): google.maps.LatLngLiteral {
    return {
      lng: location.longitude,
      lat: location.latitude,
    };
  }

}
