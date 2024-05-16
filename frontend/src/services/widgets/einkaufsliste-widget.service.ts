import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environment/environment";
import {Entry, EntryAddCommand, EntryCheckCommand, ShoppingWidget} from "../../model/shoppinglist-widget";

@Injectable({
  providedIn: 'root'
})
export class EinkaufslisteWidgetService {

  constructor(private http: HttpClient) {
  }

  addEntry(eventId: string, widgetId: string, entryAddCommand: EntryAddCommand): Observable<ShoppingWidget> {
    return this.http.post<ShoppingWidget>(`${environment.api}/event/${eventId}/widgets/shopping-list/${widgetId}/entries`, entryAddCommand, {withCredentials: true});
  }

  editEntry(eventId: string, widgetId: string, entry: Entry) {
    return this.http.post<ShoppingWidget>(`${environment.api}/event/${eventId}/widgets/shopping-list/${widgetId}/entries/${entry.id}`, entry, {withCredentials: true});
  }

  setBuyerId(eventId: string, widgetId: string, entry: Entry, value: EntryCheckCommand) {
    return this.http.put<ShoppingWidget>(`${environment.api}/event/${eventId}/widgets/shopping-list/${widgetId}/entries/${entry.id}`, value, {withCredentials: true});

  }
}
