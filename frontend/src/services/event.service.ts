import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../environment/environment";
import {EventOverview, Event, CreateEventCommand} from "../model/event";

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

  createEvent(eventData: CreateEventCommand): Observable<Event> {
    return this.http.post<Event>(`${environment.api}/event/`, eventData, {withCredentials: true});
  }

  updateEvent(eventId: string, eventData: CreateEventCommand): Observable<Event> {
    return this.http.put<Event>(`${environment.api}/event/${eventId}`, eventData, {withCredentials: true});
  }

  generateInvitationLink(eventId: string): Observable<Event> {
    return this.http.get<Event>(`${environment.api}/event/${eventId}/generateInvitationLink`, {withCredentials: true});
  }

  leaveEvent(eventId: string): Observable<any> {
    return this.http.get(`${environment.api}/event/${eventId}/leave`, {withCredentials: true});
  }
}
