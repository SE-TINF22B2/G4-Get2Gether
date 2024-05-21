import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environment/environment";
import {Entry, EntryCheckCommand, EntryCommand, ShoppingWidget} from "../../model/shoppinglist-widget";
import {Event} from "../../model/event";

@Injectable({
  providedIn: 'root'
})
export class EinkaufslisteWidgetService {

  constructor(private http: HttpClient) {
  }

  createShoppingWidget(eventId: string): Observable<Event> {
    return this.http.post<Event>(`${environment.api}/event/${eventId}/widgets/shopping-list/`, {},{withCredentials: true});
  }

  addEntry(eventId: string, widgetId: string, entryCommand: EntryCommand): Observable<ShoppingWidget> {
    return this.http.post<ShoppingWidget>(`${environment.api}/event/${eventId}/widgets/shopping-list/${widgetId}/entries`, entryCommand, {withCredentials: true});
  }

  editEntry(eventId: string, widgetId: string, entryId: string, entry: EntryCommand) {
    return this.http.put<ShoppingWidget>(`${environment.api}/event/${eventId}/widgets/shopping-list/${widgetId}/entries/update/${entryId}`, entry, {withCredentials: true});
  }

  checkEntry(eventId: string, widgetId: string, entry: Entry, value: EntryCheckCommand) {
    return this.http.put<ShoppingWidget>(`${environment.api}/event/${eventId}/widgets/shopping-list/${widgetId}/entries/${entry.id}`, value, {withCredentials: true});
  }

  deleteEntry(eventId: string, widgetId: string, entryId: string){
    return this.http.delete<ShoppingWidget>(`${environment.api}/event/${eventId}/widgets/shopping-list/${widgetId}/entries/${entryId}`, {withCredentials: true});
  }
}
