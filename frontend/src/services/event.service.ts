import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, ReplaySubject} from "rxjs";
import {User} from "../model/user";
import {environment} from "../environment/environment";
import {EventOverview} from "../model/event";
import {MapWidget} from "../model/map-widget";

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private http: HttpClient) {
  }

  getOwnEvents(): Observable<EventOverview[]> {
    return this.http.get<EventOverview[]>(`${environment.api}/event/own`, {withCredentials: true});
  }

}
