import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LocationAddCommand, MapWidget} from "../../model/map-widget";
import {Observable} from "rxjs";
import {environment} from "../../environment/environment";
import {Event} from "../../model/event";

@Injectable({
  providedIn: 'root'
})
export class MapWidgetService {

  constructor(private http: HttpClient) {
  }

  createMapWidget(eventId: string): Observable<Event> {
    return this.http.post<Event>(`${environment.api}/event/${eventId}/widgets/map/`, {},{withCredentials: true});
  }

  addLocation(eventId: string, widgetId: string, addCommand: LocationAddCommand): Observable<MapWidget> {
    return this.http.post<MapWidget>(`${environment.api}/event/${eventId}/widgets/map/${widgetId}/locations`, addCommand, {withCredentials: true});
  }

  deleteLocation(eventId: string, widgetId: string, locationId: string): Observable<MapWidget> {
    return this.http.delete<MapWidget>(`${environment.api}/event/${eventId}/widgets/map/${widgetId}/locations/${locationId}`, {withCredentials: true});
  }
}
