import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environment/environment";
import {Event} from "../../model/event";
import {Car, CarAddCommand, CarpoolWidget, RiderAddCommand} from "../../model/carpool-widget";

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

  editCar() {
    //TODO: noch nicht im Backend implementiert
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
