import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../environment/environment";
import {EventOverview, Event} from "../model/event";

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private http: HttpClient) {
  }

  getOwnEvents(): Observable<EventOverview[]> {
    return this.http.get<EventOverview[]>(`${environment.api}/event/own`, {withCredentials: true});
  }

  getSingleEvent(id: string): Observable<Event> {
    return this.http.get<Event>(`${environment.api}/event/${id}`, {withCredentials: true});
  }

}
