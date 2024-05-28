import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environment/environment";
import {Event} from "../../model/event";
import {Car, CarAddCommand, CarpoolWidget, CarUpdateCommand, RiderAddCommand} from "../../model/carpool-widget";

@Injectable({
  providedIn: 'root'
})
export class CarpoolWidgetService {

  constructor(private http: HttpClient) {
  }

  createCarPoolWidget(eventId: string): Observable<Event> {
    return this.http.post<Event>(`${environment.api}/event/${eventId}/widgets/carpool/`, {}, {withCredentials: true});
  }

  addCar(eventId: string, widgetId: string, addCommand: CarAddCommand): Observable<CarpoolWidget> {
    return this.http.post<CarpoolWidget>(`${environment.api}/event/${eventId}/widgets/carpool/${widgetId}/cars`, addCommand, {withCredentials: true});
  }

  editCar(eventId: string, widgetId: string,carId: string, carUpdateCommand: CarUpdateCommand): Observable<CarpoolWidget> {
    return this.http.patch<CarpoolWidget>(`${environment.api}/event/${eventId}/widgets/carpool/${widgetId}/cars/${carId}`, carUpdateCommand, {withCredentials: true});
  }

  addRider(eventId: string, widgetId: string,carId: string, riderAddCommand: RiderAddCommand): Observable<CarpoolWidget> {
    return this.http.post<CarpoolWidget>(`${environment.api}/event/${eventId}/widgets/carpool/${widgetId}/cars/${carId}`, riderAddCommand, {withCredentials: true})
  }

  deleteCar(eventId: string, widgetId: string,carId: string): Observable<CarpoolWidget> {
    return this.http.delete<CarpoolWidget>(`${environment.api}/event/${eventId}/widgets/carpool/${widgetId}/cars/${carId}`, {withCredentials: true});
  }

  deleteRider(eventId: string, widgetId: string,carId: string, riderId: string): Observable<CarpoolWidget> {
    return this.http.delete<CarpoolWidget>(`${environment.api}/event/${eventId}/widgets/carpool/${widgetId}/cars/${carId}/riders/${riderId}`, {withCredentials: true});
  }





}
